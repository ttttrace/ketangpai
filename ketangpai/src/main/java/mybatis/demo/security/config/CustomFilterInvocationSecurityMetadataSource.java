package mybatis.demo.security.config;

import mybatis.demo.security.mapper.MenuMapper;
import mybatis.demo.security.model.Menu;
import mybatis.demo.security.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomFilterInvocationSecurityMetadataSource
        implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuMapper menuMapper;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
	 * -该方法的参数是一个 FilterInvocation 开发者可以从 Filterlnvocation 中提取出当前请求的
	 * URL ，返回值是 Collection<ConfigAttribute> ，表示当前请求 URL 所需的角色。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
    	System.out.println("执行FilterInvocationSecurityMetadataSource");
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> allMenus = menuMapper.getAllMenus();
        for (Menu menu : allMenus) {
            if (antPathMatcher.match(menu.getPattern(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] roleArr = new String[roles.size()];
                for (int i = 0; i < roleArr.length; i++) {
                    roleArr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(roleArr);
            }
        }
      //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
