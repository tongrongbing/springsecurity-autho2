package com.wenba;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：tongrongbing
 * @date：created in 2020/7/19 19:22
 * @description：认证服务器启动类
 */
@SpringBootApplication
@RestController
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class,args);
    }

    @GetMapping("/get")
    public Object getAuthentication(){
        return "---------------------";
    }
}