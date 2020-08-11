package com.wenba.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * @author：tongrongbing
 * @date：created in 2020/7/19 20:07
 * @description：
 */
@Configuration
@EnableAuthorizationServer  // 开启认证服务功能
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private TokenStore tokenStore;  //  令牌管理策略

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    // 使用 JDBC 方式管理客户端信息
    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean // 授权码管理 存入mysql里，授权码存储到数据库意义不大，因为每次授权时候，都会删除先前的授权码
    public AuthorizationCodeServices jdbcAuthorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * @author: tongrongbing
     * @description:  配置能够访问认证服务器的客户端信息
     *                1.使用内存方式 2.数据库存储方式
     * @time: 2020/7/19 20:10
     * @param clients
     * @return void
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       /* clients.inMemory()  // 使用内存方式
                .withClient("mengxuegu-pc") // 客户端id
                // 客户端密码，要加密,不然一直要求登录, 获取不到令牌, 而且一定不能被泄露
                .secret(passwordEncoder.encode("mengxuegu-secret"))
               .resourceIds("product-server")  // 资源id, 针对的 是微服务名称，订单服务
                // 授权类型, 可同时支持多种授权类型
               .authorizedGrantTypes("authorization_code", "password", "implicit","client_credentials","refresh_token")
                 // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                .scopes("all")
                .autoApprove(false) // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码
                .redirectUris("http://www.baidu.com/") // 客户端回调地址
                .accessTokenValiditySeconds(60*60*2)  // 访问令牌的有效期，默认是12小时这里设置为2小时
                .refreshTokenValiditySeconds(60*60*24)  // 刷新令牌的有效期，默认是30天，这里设置为1天*/
        clients.withClientDetails(jdbcClientDetailsService());
        ;
    }

    /**
     * @author: tongrongbing
     * @description:    认证服务端点配置
     * @time: 2020/8/4 11:36 上午
     * @param endpoints
     * @return void
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 密码模式要设置认证管理器
        endpoints.authenticationManager(authenticationManager);
        //刷新令牌获取新令牌时需要
        endpoints.userDetailsService(customUserDetailsService);
        // 令牌管理策略
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter); // 利用jwt来管理令牌
        // 授权码管理 针对授权码模式有效，会将授权码放到 auth_code 表，授权后就会删除它
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
    }

    // 令牌端点权限配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 所有人都可以访问/oauth/token_key 后面要获取公钥, 默认拒绝访问
        security.tokenKeyAccess("permitAll()");
        // 认证后可访问 /oauth/check_token , 默认拒绝访问
        security.checkTokenAccess("isAuthenticated()");
    }
}