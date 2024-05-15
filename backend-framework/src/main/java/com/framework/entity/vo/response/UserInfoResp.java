package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResp {

    //点赞文章集合
    private List<ArticlePaginationResp> articleLikeSet = null;
    //用户头像
    private String avatar;
    //点赞评论集合
    private List<CommentResp> commentLikeSet = null;
    //用户邮箱
    private String email;
    //用户id
    private Integer id;
    //个人简介
    private String intro;
    //登录类型
    private Integer loginType;
    //用户昵称
    private String nickname;
    //点赞说说集合 TODO
    private List<?> talkLikeSet = null;
    //用户名
    private String username;
    //个人网站
    private String webSite;
}