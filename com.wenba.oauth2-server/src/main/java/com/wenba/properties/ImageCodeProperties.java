package com.wenba.properties;

/**
 * @description:  图片校验码配置类
 * @author: tongrongbing
 * @date: 2020-08-06 11:18
 **/
public class ImageCodeProperties extends SmsCodeProperties{

    private int width = 67;  // 图片验证码的宽度

    private int height = 23; // 图片验证码的高度

    @Override
    public void setLength(int length) {
        super.setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
