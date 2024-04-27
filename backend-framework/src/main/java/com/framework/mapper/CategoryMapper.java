package com.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.entity.dao.Category;
import com.framework.entity.vo.response.CategoryOptionResp;
import org.apache.ibatis.annotations.Select;

/**
 * (Category)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-24 14:21:06
 */
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("select * from t_category where id = #{id}")
    CategoryOptionResp getCategoryOption(Integer id);
}

