package com.wenba.config.oauth2;
import com.wenba.config.FormAuthenticationConfig;
import com.wenba.config.ValidateCodeSecurityConfig;
import com.wenba.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


/**
 * @author：tongrongbing
 * @date：created in 2020/7/12 22:44
 * @description：
 */
@Configuration
@EnableResourceServer
public class AuthorizationResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        formAuthenticationConfig.configure(http); // 表单认证
        http
                /*.apply(validateCodeSecurityConfig)
                .and()*/
        .authorizeRequests()
                .antMatchers(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                        "/imooc-signIn.html","/auth/authentication/form",
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("product-server");
    }
}
