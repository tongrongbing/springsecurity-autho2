package com.wenba.validate.core.processor;

import com.wenba.enums.ValidateCodeType;
import com.wenba.validate.core.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description:  验证码的处理器管理器，主要是用来查找具体的验证码处理器
 * @author: tongrongbing
 * @date: 2020-08-06 15:24
 **/
@Component
public class ValidateCodeProcessorHolder {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * @author: tongrongbing
     * @description:   根据验证码类型查找具体的验证码处理器
     * @time: 2020/8/8 12:02 下午
     * @param type
     * @return com.wenba.validate.core.processor.ValidateCodeProcessor
     */
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type){
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    /**
     * @author: tongrongbing
     * @description:   根据前端传来的String验证码类型,如image、sms 来查找具体的验证码处理器
     * @time: 2020/8/8 12:06 下午
     * @param type
     * @return com.wenba.validate.core.processor.ValidateCodeProcessor
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type){
        String name = type + ValidateCodeProcessor.class.getSimpleName(); // 如name = image + ValidateCodeProcessor
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(name);
        if(validateCodeProcessor == null){
            throw new ValidateCodeException("验证码处理器"+name + "不存在...");
        }
        return validateCodeProcessor;
    }

}
