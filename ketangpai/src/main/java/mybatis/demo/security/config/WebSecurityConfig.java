package mybatis.demo.security.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import mybatis.demo.security.service.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	System.out.println("执行WebSecurityConfigurerAdapter");
        http.authorizeRequests()
        // 可以在Service层通过注解配置方法安全 详情见StudentService
		        .antMatchers("/teacher/**")
		        .hasRole("teacher")
		        .antMatchers("/student/**")
		        .hasRole("student")
		        .antMatchers("/user/register/**")
		        .permitAll()
		        .antMatchers("/ketangpai/**")
		        .permitAll()
		        .anyRequest()
		        .authenticated()
		        .and()
                .formLogin()
//                .loginPage("/login_page")
                .loginProcessingUrl("/login")
                .permitAll()
//                .usernameParameter("name")
//                .passwordParameter("password/")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        Authentication auth)
                            throws IOException {
                        Object principal = auth.getPrincipal();
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        resp.setStatus(200);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 200);
                        map.put("msg", principal);
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
//                .successForwardUrl("/hello")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req,
                                                        HttpServletResponse resp,
                                                        AuthenticationException e)
                            throws IOException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        resp.setStatus(401);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 401);
                        if (e instanceof LockedException) {
                            map.put("msg", "账户被锁定，登录失败!");
                        } else if (e instanceof BadCredentialsException) {
                            map.put("msg", "账户名或密码输入错误，登录失败!");
                        } else if (e instanceof DisabledException) {
                            map.put("msg", "账户被禁用，登录失败!");
                        } else if (e instanceof AccountExpiredException) {
                            map.put("msg", "账户已过期，登录失败!");
                        } else if (e instanceof CredentialsExpiredException) {
                            map.put("msg", "密码已过期，登录失败!");
                        } else {
                            map.put("msg", "登录失败!");
                        }
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest req,
                                       HttpServletResponse resp,
                                       Authentication auth) {

                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req,
                                                HttpServletResponse resp,
                                                Authentication auth)
                            throws IOException {
//                        resp.sendRedirect("/login_page");
                    	
                    }
                })
                .and()
                // 跨域配置
                .cors() 
                .and()   
                .csrf() 
                
                .disable().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                	 @Override
                	 public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
                	 resp.setContentType("application/json;charset=utf-8");
                	 resp.setStatus(401);
                	 PrintWriter out = resp.getWriter();
                	 String msg="请求失败";
                	 if (authException instanceof InsufficientAuthenticationException) {
                		msg = "请求失败，请联系管理员!";
                	 }
                	 out.write(new ObjectMapper().writeValueAsString(msg));
                	 out.flush();
                	 out.close();
                	 }
                	 });
    }
    
    @Bean
    CustomFilterInvocationSecurityMetadataSource cfisms() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }
    
    @Bean
    CustomAccessDecisionManager cadm() {
        return new CustomAccessDecisionManager();
    }
    
}
