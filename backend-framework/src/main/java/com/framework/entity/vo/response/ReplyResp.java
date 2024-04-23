package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReplyResp {
    //头像
    private String avatar;
    //评论内容
    private String commentContent;
    //评论时间
    private String createTime;
    //昵称
    private String fromNickname;
    //评论用户id
    private Integer fromUid;
    //评论id
    private Integer id;
    //点赞数
    private Integer likeCount;
    //父级评论id
    private Integer parentId;;
    //被评论用户昵称
    private String toNickname;;
    //被评论用户id
    private Integer toUid;
    //个人网站
    private String webSite;
}
