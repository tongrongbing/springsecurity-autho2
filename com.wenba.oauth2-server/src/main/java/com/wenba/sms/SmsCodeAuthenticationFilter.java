package com.wenba.sms;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author：tongrongbing
 * @date：created in 2020/8/12 22:32
 * @description： 短信验证码认证过滤器
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_PHONENUM_KEY = "phoneNum";
    private String phoneNumParameter = SPRING_SECURITY_FORM_PHONENUM_KEY;
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/authentication/mobile", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String phoneNum = obtainPhoneNum(request);
        if (phoneNum == null) {
            phoneNum = "";
        }
        phoneNum = phoneNum.trim();
        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(phoneNum);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * @author: tongrongbing
     * @description:  获取手机号
     * @time: 2020/8/12 22:46
     * @param request
     * @return java.lang.String
     */
    @Nullable
    protected String obtainPhoneNum(HttpServletRequest request) {
        return request.getParameter(phoneNumParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              SmsAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param phoneNumParameter the parameter name. Defaults to "username".
     */
    public void setPhoneNumParameter(String phoneNumParameter) {
        Assert.hasText(phoneNumParameter, "phoneNumParameter parameter must not be empty or null");
        this.phoneNumParameter = phoneNumParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneNumParameter() {
        return phoneNumParameter;
    }

}