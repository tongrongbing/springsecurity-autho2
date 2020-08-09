package com.wenba.service.sms;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wenba.properties.SecurityProperties;
import com.wenba.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author：tongrongbing
 * @date：created in 2020/8/8 21:46
 * @description：短信服务
 */
@Component("smsService")
@Slf4j
public class SmsService {

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private RedisService redisService;

    public Boolean send(String mobile,String verifyCode) {
        DefaultProfile profile = DefaultProfile.getProfile("default", properties.getSms().getAccessKeyID(), properties.getSms().getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        log.info("生成的验证码是，{}",verifyCode);
        request.setMethod(MethodType.POST);
        request.setDomain(properties.getSms().getDomain());
        request.setVersion(properties.getSms().getVersion());
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", properties.getSms().getRegionId());
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "启航");
        request.putQueryParameter("TemplateCode", properties.getSms().getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":"+ verifyCode +"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObejct = JSONObject.parseObject(response.getData());
            String code = jsonObejct.getString("Code");
            if("OK".equals(code)) {
                log.info("发送验证码成功....");
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}