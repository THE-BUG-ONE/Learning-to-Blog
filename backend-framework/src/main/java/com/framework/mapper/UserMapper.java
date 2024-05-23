package com.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.entity.dao.User;
import org.apache.ibatis.annotations.Select;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-08 14:09:04
 */
public interface UserMapper extends BaseMapper<User> {
}

