package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageBackResp {
    /**
     * 留言id
     */
    private Integer id;

    /**
     * (0 留言/其他 回复评论id)
     */
    private Integer parentId;

    /**
     * 根评论id
     */
    private Integer rootId;

    /**
     * 回复评论用户昵称
     */
    private String fromNickname;

    /**
     * 用户昵称
     */
    private UserOptionResp user;

    /**
     * 留言内容
     */
    private String message;

    /**
     * 是否通过 (0否 1是)
     */
    private Integer isCheck;

    /**
     * 留言时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
