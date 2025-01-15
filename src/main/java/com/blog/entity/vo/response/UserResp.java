package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResp {
    //用户id
    private Integer id;
    //用户名
    private String nickname;
    //用户角色
    private List<String> roleList;
    //头像
    private String avatar;
}
