package com.wenba.enums;

import com.wenba.constants.SecurityConstants;

/**
 * @description: 校验码枚举类
 * @author: tongrongbing
 * @date: 2020-08-06 10:51
 **/
public enum ValidateCodeType {

    /**
     * @author: tongrongbing
     * @description:    图片校验码类型
     * @time: 2020/8/6 11:03 上午
     *
     */
    IMAGE{
        @Override
        public String getValidateCodeType() {
            return SecurityConstants.DEFAULT_VALIDATE_CODE_IMAGE;
        }
    },

    /**
     * @author: tongrongbing
     * @description:    手机号校验码类型
     * @time: 2020/8/6 11:03 上午
     *
     */
    SMS{
        @Override
        public String getValidateCodeType() {
            return SecurityConstants.DEFAULT_VALIDATE_CODE_SMS;
        }
    }
    ;

    public abstract String getValidateCodeType();
}
