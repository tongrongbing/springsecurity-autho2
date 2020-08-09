package com.wenba.validate.core.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description:  验证码处理器
 * @author: tongrongbing
 * @date: 2020-08-06 15:27
 **/
public interface ValidateCodeProcessor {

    /**
     * @author: tongrongbing
     * @description:  验证码处理（包括创建、保存、发送）
     * @time: 2020/8/6 3:28 下午
     * @param request
     * @return void
     */
    void process(ServletWebRequest request) throws Exception;

    /**
     * @author: tongrongbing
     * @description:  校验验证码
     * @time: 2020/8/6 3:28 下午
     * @param request
     * @return void
     */
    void validate(ServletWebRequest request) ;

}
