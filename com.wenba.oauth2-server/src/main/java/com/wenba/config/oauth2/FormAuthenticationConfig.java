package com.wenba.config.oauth2;

import com.wenba.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @description:  表单登录配置
 * @author: tongrongbing
 * @date: 2020-08-05 14:07
 **/
@Component("formAuthenticationConfig")
public class FormAuthenticationConfig {
    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler);
    }
}
