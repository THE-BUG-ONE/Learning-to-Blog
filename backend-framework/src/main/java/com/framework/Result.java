package com.framework;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.framework.utils.enums.AppHttpCodeEnum;

public record Result<T>(boolean flag, int code, String msg, T data) {
    public static <T> Result<T> success(T data) {
        return new Result<>(
                true,
                AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMsg(),
                data);
    }
    public static <T> Result<T> success() {
        return new Result<>(
                true,
                AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMsg(),
                null);
    }

    public static <T> Result<T> failure() {
        return new Result<>(
                false,
                AppHttpCodeEnum.FORBIDDEN.getCode(),
                AppHttpCodeEnum.FORBIDDEN.getMsg(),
                null);
    }

    public static <T> Result<T> failure(AppHttpCodeEnum appHttpCodeEnum) {
        return new Result<>(
                false,
                appHttpCodeEnum.getCode(),
                appHttpCodeEnum.getMsg(),
                null);
    }

    public String asJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
