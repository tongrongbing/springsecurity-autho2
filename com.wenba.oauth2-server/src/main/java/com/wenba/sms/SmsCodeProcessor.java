package com.wenba.sms;

import com.wenba.constants.SecurityConstants;
import com.wenba.service.sms.SmsService;
import com.wenba.validate.core.entity.ValidateCode;
import com.wenba.validate.core.processor.AbstractValidateCodeProcessorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author：tongrongbing
 * @date：created in 2020/8/8 22:42
 * @description：短信服务验证码处理器
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessorHandler<ValidateCode> {

    @Autowired
    private SmsService smsService;

    @Override
    public void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
        smsService.send(mobile,validateCode.getCode());
    }
}