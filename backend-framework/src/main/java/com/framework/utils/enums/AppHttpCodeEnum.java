package com.framework.utils.enums;

import lombok.Getter;

@Getter
public enum AppHttpCodeEnum {
    SUCCESS(200, "请求成功"),
    UNAUTHORIZED(401,"未登录"),
    FORBIDDEN(403, "请求无权限")

    ;

    final int code;
    final String msg;
    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
