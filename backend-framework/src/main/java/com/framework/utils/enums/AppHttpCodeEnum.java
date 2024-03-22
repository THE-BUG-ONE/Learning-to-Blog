package com.framework.utils.enums;

import lombok.Getter;

@Getter
public enum AppHttpCodeEnum {
    SUCCESS(200, "请求成功"),
    NEED_LOGIN(401,"请登录后操作"),

    ;

    int code;
    String msg;
    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
