package com.wenba.validate.core.repository;

import com.wenba.enums.ValidateCodeType;
import com.wenba.validate.core.entity.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description:  验证码的存储器
 * @author: tongrongbing
 * @date: 2020-08-08 17:20
 **/
public interface ValidateCodeRepository {
    /**
     * @author: tongrongbing
     * @description:  保存验证码
     * @time: 2020/7/5 22:10
     * @param request
     * @param code
     * @param validateCodeType
     * @return void
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * @author: tongrongbing
     * @description:  获取验证码
     * @time: 2020/7/5 22:10
     * @param request
     * @param validateCodeType
     * @return com.imooc.security.core.validate.core.ValidateCode
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * @author: tongrongbing
     * @description:   移除验证码
     * @time: 2020/7/5 22:10
     * @param request
     * @param codeType
     * @return void
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);



}
