package com.wenba.image;

import com.wenba.validate.core.entity.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @description:  图片验证码
 * @author: tongrongbing
 * @date: 2020-08-06 15:34
 **/
public class ImageCode extends ValidateCode {

    private BufferedImage image;

    public ImageCode(BufferedImage image) {
        this.image = image;
    }

    public ImageCode(String code, int expireIn, BufferedImage image) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(String code, LocalDateTime expireTime, BufferedImage image) {
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
