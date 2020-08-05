package com.wenba.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：tongrongbing
 * @date：created in 2020/7/4 18:25
 * @description：
 */
@Component("successHandler")
@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     * @author: tongrongbing
     * @description:        Authentication:封装认证信息 包括用户信息等
     * @time: 2020/7/4 18:26
     * @param request
     * @param response
     * @param authentication
     * @return void
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
         log.info("登陆成功...........");

    }
}