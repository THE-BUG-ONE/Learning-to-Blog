package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TalkResp {
    //说说id
    private Integer id;
    //说说内容
    private String talkContent;
    //说说图片
    private List<String> imgList;
    //是否置顶 (0否 1是)
    private Integer isTop;
    //发表时间
    private Date createTime;
    //评论量
    private Integer commentCount;
    //头像
    private String avatar;
    //点赞量
    private Long likeCount;
    //昵称
    private String nickname;
}
