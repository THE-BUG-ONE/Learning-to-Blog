package com.framework.utils.enums;

import lombok.Getter;

@Getter
public enum AppHttpCodeEnum {
    SUCCESS(200, "请求成功"),
    UNAUTHORIZED(401,"请登录后操作"),
    FORBIDDEN(403, "禁止")

    ;

    final int code;
    final String msg;
    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
