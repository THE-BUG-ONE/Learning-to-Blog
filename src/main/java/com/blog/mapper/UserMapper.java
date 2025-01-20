package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.User;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.UserBackResp;
import com.blog.entity.vo.response.UserOptionResp;
import com.blog.entity.vo.response.UserResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-08 14:09:04
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select username from user where id = #{id}")
    String getUsernameById(Integer id);

    @Select("select nickname from user where id = #{id}")
    String getNicknameById(Integer id);

    @Select("select id, username from user where username like concat('%', #{username}, '%')")
    List<UserOptionResp> getUserOptionList(String username);

    @Select("select * from user limit #{param.page},#{param.limit}")
    @Results(id = "getBackUserListMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "roleList",
                    many = @Many(select = "com.blog.mapper.RoleMapper.getUserRoleNameList"))
    })
    List<UserBackResp> getBackUserList(@Param("param")PageReq req);

    @Select("select * from user where id = #{id}")
    @Results(id = "getBackUserMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "roleList",
                    many = @Many(select = "com.blog.mapper.RoleMapper.getUserRoleNameList"))
    })
    UserBackResp getBackUserById(Integer id);

    @Select("select id,nickname,avatar from user where id = #{id}")
    @Results(id = "getUserRespMap", value = {
            @Result(column = "id", property = "roleList",
                    many = @Many(select = "com.blog.mapper.RoleMapper.getUserRoleNameList"))
    })
    UserResp getUserRespById(Integer id);

    @Select("select id,nickname from user where id = #{id}")
    UserOptionResp getUserOptionRespById(Integer id);
}

