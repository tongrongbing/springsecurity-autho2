package com.wenba.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @author：tongrongbing
 * @date：created in 2020/7/19 19:24
 * @description：
 */
@Data
public class APIResponse implements Serializable {
    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public APIResponse() {
    }
    public APIResponse(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public APIResponse(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public APIResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static APIResponse ok() {
        return new APIResponse(null);
    }
    public static APIResponse ok(String message) {
        return new APIResponse(message, null);
    }
    public static APIResponse ok(Object data) {
        return new APIResponse(data);
    }
    public static APIResponse ok(String message, Object data) {
        return new APIResponse(message, data);
    }

    public static APIResponse build(Integer code, String message) {
        return new APIResponse(code, message, null);
    }

    public static APIResponse build(Integer code, String message, Object data) {
        return new APIResponse(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 MengxueguResult 对象
     * @param json
     * @return
     */
    public static APIResponse format(String json) {
        try {
            return JSON.parseObject(json, APIResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}