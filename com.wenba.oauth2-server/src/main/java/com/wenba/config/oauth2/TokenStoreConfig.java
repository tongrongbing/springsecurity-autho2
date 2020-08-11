package com.wenba.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * @author：tongrongbing
 * @date：created in 2020/7/19 23:11
 * @description： token配置策略-------> redis存储和jdbc存储方式
 */
@Configuration
public class TokenStoreConfig {

    private static final String SIGNINGKEY = "wenba.com";
   /* @Autowired
    private RedisConnectionFactory redisConnectionFactory;  // redis 管理token
    */

    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore(){
        //return new RedisTokenStore(redisConnectionFactory);  // redis管理令牌
        //return new JdbcTokenStore(dataSource); // jdbc管理令牌
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(SIGNINGKEY);
        return tokenConverter;
    }
}