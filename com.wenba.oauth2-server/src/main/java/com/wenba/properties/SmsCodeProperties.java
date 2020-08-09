package com.wenba.properties;

/**
 * @description:  手机号校验码配置类
 * @author: tongrongbing
 * @date: 2020-08-06 11:19
 **/
public class SmsCodeProperties {
    private int length = 4; // 长度

    private int expiredIn = 60;  // 有效期

    private String url;  // 过滤的URL

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpiredIn() {
        return expiredIn;
    }

    public void setExpiredIn(int expiredIn) {
        this.expiredIn = expiredIn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
