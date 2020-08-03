package com.wenba.oauth2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author：tongrongbing
 * @date：created in 2020/7/21 22:32
 * @description：
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testPassword(){
        System.out.println(passwordEncoder.encode("mengxuegu-secret"));
    }
}