package com.framework.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RecentCommentResp {
    //头像
    private String avatar;
    //评论内容
    private String commentContent;
    //评论时间
    private String createTime;
    //昵称
    private String nickname;
    //评论id
    private Integer id;
}
