package com.wenba.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:  全局配置类
 * @author: tongrongbing
 * @date: 2020-08-06 11:14
 **/
@ConfigurationProperties(prefix = "wenba.security")
public class SecurityProperties {

    private ValidateCodeProperties validateCode = new ValidateCodeProperties();

    private SmsProperties sms = new SmsProperties();

    public ValidateCodeProperties getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(ValidateCodeProperties validateCode) {
        this.validateCode = validateCode;
    }

    public SmsProperties getSms() {
        return sms;
    }

    public void setSms(SmsProperties sms) {
        this.sms = sms;
    }
}
