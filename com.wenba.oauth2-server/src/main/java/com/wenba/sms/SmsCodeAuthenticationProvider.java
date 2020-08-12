package com.wenba.sms;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author：tongrongbing
 * @date：created in 2020/8/12 22:47
 * @description： 短信验证码认证提供器---处理短信认证逻辑
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * @author: tongrongbing
     * @description:         短信认证逻辑
     * @time: 2020/8/12 22:49
     * @param authentication
     * @return org.springframework.security.core.Authentication
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken)authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        // 重新构造认证结果
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails,userDetails.getAuthorities());
        authenticationResult.setDetails(smsAuthenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * @author: tongrongbing
     * @description:  authentication 类型 是否SmsAuthenticationToken的子类
     * @time: 2020/8/12 22:49
     * @param authentication
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}