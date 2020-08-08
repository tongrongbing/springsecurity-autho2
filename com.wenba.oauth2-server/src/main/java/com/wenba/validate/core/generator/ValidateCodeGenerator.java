package com.wenba.validate.core.generator;

import com.wenba.validate.core.entity.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description:  验证码生成器
 * @author: tongrongbing
 * @date: 2020-08-08 11:41
 **/
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
