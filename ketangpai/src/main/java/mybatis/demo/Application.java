package mybatis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration  
@SpringBootApplication 
public class Application {
	public static void main(String[] args) {  
        SpringApplication.run(Application.class, args);  
    } 
}
