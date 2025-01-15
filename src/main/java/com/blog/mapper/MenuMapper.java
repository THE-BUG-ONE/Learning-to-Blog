package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Felz
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2024-11-02 14:15:13
* @Entity com.blog.entity.dao.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}




