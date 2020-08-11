package com.wenba.sms;

import com.wenba.properties.SecurityProperties;
import com.wenba.validate.core.entity.ValidateCode;
import com.wenba.validate.core.generator.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author：tongrongbing
 * @date：created in 2020/8/8 22:30
 * @description：短信服务验证码生成器
 */
@Component("smsValidateCodeGenerator")
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties properties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(properties.getValidateCode().getSmsCode().getLength());
        return new ValidateCode(code,properties.getValidateCode().getSmsCode().getExpiredIn());
    }
}