package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Role;
import com.blog.entity.vo.response.UserRoleResp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Role)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-19 14:16:04
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select role_name from role where id in (select role_id from user_role where user_id = #{userId})")
    List<String> getRoleList(int userId);

    @Select("select id, role_name from role where id in (select role_id from user_role where user_id = #{userId})")
    List<UserRoleResp> getUserRoleList(Integer userId);

}

