package com.framework;

import com.framework.utils.enums.AppHttpCodeEnum;

public record RestBean<T>(int code, String msg, T data) {
    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(
                AppHttpCodeEnum.SUCCESS.getCode(),
                AppHttpCodeEnum.SUCCESS.getMsg(),
                data);
    }
}
