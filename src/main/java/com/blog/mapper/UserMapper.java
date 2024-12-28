package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.User;
import com.blog.entity.vo.response.UserOptionResp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select id, username from user where username like concat('%', #{username}, '%')")
    List<UserOptionResp> getUserOptionList(String username);
}

