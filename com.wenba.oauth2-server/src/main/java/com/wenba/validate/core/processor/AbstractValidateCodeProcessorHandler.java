package com.wenba.validate.core.processor;

import com.wenba.enums.ValidateCodeType;
import com.wenba.validate.core.entity.ValidateCode;
import com.wenba.validate.core.generator.ValidateCodeGenerator;
import com.wenba.validate.core.exception.ValidateCodeException;
import com.wenba.validate.core.repository.ValidateCodeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @description:  抽象校验码处理器 实现ValidateCodeProcessor，提供抽象发送方法供子类自定义实现
 * @author: tongrongbing
 * @date: 2020-08-06 15:31
 **/
public abstract class AbstractValidateCodeProcessorHandler<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator}接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository redisValidateCodeRepository;

    /**
     * @author: tongrongbing
     * @description:   创建验证码，并且保存验证码且发送验证码
     * @time: 2020/8/8 11:35 上午
     * @param request
     * @return void
     */
    @Override
    public void process(ServletWebRequest request) throws Exception {
        // 生成校验码
        C validateCode = generate(request);
        // 保存校验码
        save(request, validateCode);
        // 发送校验码
        send(request, validateCode);
    }

    private C generate(ServletWebRequest request){
        String type = getValidateCodeType().toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    private ValidateCodeType getValidateCodeType(){
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    private void save(ServletWebRequest request,C validateCode){
        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
        redisValidateCodeRepository.save(request,code,getValidateCodeType());
    }

    public abstract void send(ServletWebRequest request,C validateCode) throws Exception;

    @Override
    public void validate(ServletWebRequest request) {
        ValidateCodeType codeType = getValidateCodeType();
        C codeInRedis = (C) redisValidateCodeRepository.get(request, codeType);
        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), codeType.getValidateCodeType());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(codeType + "请填写验证码");
        }

        if (codeInRedis == null) {
            throw new ValidateCodeException(codeType + "验证码不存在");
        }

        if (codeInRedis.exist()) {
            redisValidateCodeRepository.remove(request, codeType);
            throw new ValidateCodeException(codeType + "验证码已过期，请重新获取");
        }
        if (!StringUtils.equals(codeInRedis.getCode(), codeInRequest)) {
            throw new ValidateCodeException(codeType + "验证码不正确");
        }
        redisValidateCodeRepository.remove(request, codeType);

    }
}
