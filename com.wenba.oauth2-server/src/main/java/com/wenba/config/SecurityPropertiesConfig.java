package com.wenba.config;

import com.wenba.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: tongrongbing
 * @date: 2020-08-06 11:16
 **/
@EnableConfigurationProperties(SecurityProperties.class)
@Configuration
public class SecurityPropertiesConfig {
}
