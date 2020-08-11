package com.wenba.validate.core.filter;
import com.wenba.constants.SecurityConstants;
import com.wenba.enums.ValidateCodeType;
import com.wenba.properties.SecurityProperties;
import com.wenba.validate.core.exception.ValidateCodeException;
import com.wenba.validate.core.processor.ValidateCodeProcessorHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: 校验码过滤器
 * @author: tongrongbing
 * @date: 2020-08-06 10:24
 **/
@Slf4j
@Component("validationCodeFilter")
public class ValidationCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     *配置需要进行校验码校验的URL
     */
    private final Map<String, ValidateCodeType> urlMap = new HashMap<>();

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    /**
     * @author: tongrongbing
     * @description:  ValidationCodeFilter初始化
     * @time: 2020/8/6 11:38 上午
     * @param
     * @return void
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getValidateCode().getImageCode().getUrl(),ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getValidateCode().getSmsCode().getUrl(),ValidateCodeType.SMS);
    }

    /**
     * @author: tongrongbing
     * @description:   配置的url进行分割，存入map中
     * @time: 2020/8/6 1:53 下午
     * @param urlString
     * @param type
     * @return void
     */
    private void addUrlToMap(String urlString,ValidateCodeType type){
        if(StringUtils.isNotEmpty(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for(String url : urls){
                urlMap.put(url,type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);
        if(type != null){
            log.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                failureHandler.onAuthenticationFailure(request,response,exception);
                return;
            }
        }
        chain.doFilter(request,response);
    }

    /**
     * @author: tongrongbing
     * @description:        根据请求获取请求uri判断是否与需要校验的URL匹配，匹配成功则返回对应的ValidateCodeType类型。
     * @time: 2020/8/6 2:20 下午
     * @param request
     * @return com.wenba.enums.ValidateCodeType
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request){
        ValidateCodeType type = null;
        if(!StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            Set<String> urlSet = urlMap.keySet();
            for(String url : urlSet){
                log.info("request.getRequestURI()"+request.getRequestURI());
                if(pathMatcher.match(url,request.getServletPath())){
                    type = urlMap.get(url);
                }
            }
        }
        return type;
    }


}
