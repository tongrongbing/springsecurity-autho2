package com.wenba.validate.core.processor;

import com.wenba.image.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @description:
 * @author: tongrongbing
 * @date: 2020-08-08 11:57
 **/
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessorHandler<ImageCode> {

    @Override
    public void send(ServletWebRequest request, ImageCode imageCode) throws Exception{
        ImageIO.write(imageCode.getImage(),"JPEG",request.getResponse().getOutputStream());
    }
}
