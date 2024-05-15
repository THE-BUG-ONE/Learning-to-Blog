package com.framework.entity.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoReq {
    //用户昵称
    @NonNull
    private String nickname;
    //个人网站
    private String webSite;
    //个人简介
    private String intro;
}
