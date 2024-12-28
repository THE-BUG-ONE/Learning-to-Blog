package com.blog.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserBackInfoResp {
    //用户id
    private String id;
    //头像
    private String avatar;
    //权限标识
    private List<String> permissionList;
    //角色
    private List<String> roleList;
}
