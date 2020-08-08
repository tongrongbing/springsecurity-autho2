package com.wenba.validate.core.service;

import com.wenba.constants.SecurityConstants;
import com.wenba.validate.core.processor.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:  验证码请求处理控制层：图片验证码和手机号验证
 * @author: tongrongbing
 * @date: 2020-08-06 15:16
 **/
@RestController
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;


    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable() String type) throws Exception{

    validateCodeProcessorHolder.findValidateCodeProcessor(type).process(new ServletWebRequest(request,response));

    }

}
