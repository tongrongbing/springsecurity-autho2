package com.wenba.core.config;

import com.wenba.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @description:  自动开启SecurityProperties配置文件的自动配置
 * @author: tongrongbing
 * @date: 2020-08-04 15:19
 **/
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesConfig {
}
