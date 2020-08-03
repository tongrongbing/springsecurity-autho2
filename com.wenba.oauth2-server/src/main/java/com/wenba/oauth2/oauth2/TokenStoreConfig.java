package com.wenba.oauth2.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @author：tongrongbing
 * @date：created in 2020/7/19 23:11
 * @description：
 */
@Configuration
public class TokenStoreConfig {

   /* @Autowired
    private RedisConnectionFactory redisConnectionFactory;  // redis 管理token
    */

    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore(){
        //return new RedisTokenStore(redisConnectionFactory);  // redis管理令牌
        return new JdbcTokenStore(dataSource);
    }
}