package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResp {

    //公告ID
    private Integer id;
    //公告标题
    private String title;
    //公告内容
    private String content;
    //创建时间
    private Date createTime;
}
