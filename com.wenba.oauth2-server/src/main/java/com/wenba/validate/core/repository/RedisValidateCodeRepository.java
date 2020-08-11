package com.wenba.validate.core.repository;

import com.wenba.constants.SecurityConstants;
import com.wenba.enums.ValidateCodeType;
import com.wenba.service.redis.RedisService;
import com.wenba.validate.core.entity.ValidateCode;
import com.wenba.validate.core.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: tongrongbing
 * @date: 2020-08-08 17:23
 **/
@Component("redisValidateCodeRepository")
@Slf4j
public class RedisValidateCodeRepository implements ValidateCodeRepository{

    @Autowired
    private RedisService redisService;

    /**
     * @author: tongrongbing
     * @description:        验证码存储
     * @time: 2020/8/8 5:57 下午
     * @param request
     * @param code
     * @param validateCodeType
     * @return void
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        redisService.addObject(buildKey(request,validateCodeType),code,3600);
        //redisTemplate.opsForValue().set(buildKey(request,validateCodeType),code,3600, TimeUnit.SECONDS);
    }

    /**
     * @author: tongrongbing
     * @description:            构建redis缓存key-----目前只考虑在pc端。
     * @time: 2020/8/8 5:56 下午
     * @param request
     * @param validateCodeType
     * @return java.lang.String
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String deviceId = request.getHeader("deviceId");  // 表示在app手机端登录，需要传来deviceId手机标识
        String result =  StringUtils.isBlank(deviceId) ? request.getSessionId() : deviceId;
        return SecurityConstants.REDIS_VALIDATE_CODE_KEY_PREFIX_ + validateCodeType.toString().toLowerCase() + result;
    }

    /**
     * @author: tongrongbing
     * @description:        获取redis的值
     * @time: 2020/8/8 6:23 下午
     * @param request
     * @param validateCodeType
     * @return com.wenba.validate.core.entity.ValidateCode
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        Object value = redisService.getObject(buildKey(request, validateCodeType));
        return value == null ? null : (ValidateCode)value;
    }

    /**
     * @author: tongrongbing
     * @description:       移除redis的值
     * @time: 2020/8/8 6:23 下午
     * @param request
     * @param codeType
     * @return void
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        redisService.del(buildKey(request,codeType));
    }
}
