package com.blog.utils.enums;

import lombok.Getter;

@Getter
public enum AppHttpCodeEnum {
    SUCCESS(200, "请求成功"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401,"未登录"),
    FORBIDDEN(403, "请求无权限"),
    INTERNAL_ERROR(500, "服务器内部错误")

    ;

    final int code;
    final String msg;
    AppHttpCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
