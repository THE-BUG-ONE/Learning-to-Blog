package com.framework;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.framework.utils.enums.AppHttpCodeEnum;

public record RestBean<T>(boolean flag, int code, String msg, T data) {
    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(
                true,
                AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMsg(),
                data);
    }
    public static <T> RestBean<T> success() {
        return new RestBean<>(
                true,
                AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMsg(),
                null);
    }

    public static <T> RestBean<T> failure() {
        return new RestBean<>(
                false,
                AppHttpCodeEnum.FORBIDDEN.getCode(),
                AppHttpCodeEnum.FORBIDDEN.getMsg(),
                null);
    }

    public String asJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
