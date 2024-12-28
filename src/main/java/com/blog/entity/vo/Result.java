package com.blog.entity.vo;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.blog.utils.enums.AppHttpCodeEnum;

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
        return failure(
                AppHttpCodeEnum.FORBIDDEN.getCode(),
                AppHttpCodeEnum.FORBIDDEN.getMsg()
        );
    }

    public static <T> Result<T> failure(AppHttpCodeEnum appHttpCodeEnum) {
        return failure(
                appHttpCodeEnum.getCode(),
                appHttpCodeEnum.getMsg()
        );
    }

    public static <T> Result<T> failure(int code, String msg) {
        return new Result<>(
                false,
                code,
                msg,
                null);
    }

    public String asJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }

    public static <T> Result<T> result(T res) {
        return res != null ?
                Result.success(res) :
                Result.failure(AppHttpCodeEnum.BAD_REQUEST);
    }
}
