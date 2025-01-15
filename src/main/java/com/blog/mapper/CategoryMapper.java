package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Category;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.CategoryBackResp;
import com.blog.entity.vo.response.CategoryOptionResp;
import com.blog.entity.vo.response.CategoryResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Category)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-24 14:21:06
 */
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select category_name from category where id = #{id}")
    String getCategoryNameById(Integer id);

    @Select("select id, category_name from category where id = #{id}")
    CategoryOptionResp getCategoryOption(Integer id);

    @Select("select * from category")
    @Results(id = "getCategoryMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "articleCount",
                    one = @One(select = "com.blog.mapper.ArticleMapper.countArticleByCategoryId"))
    })
    List<CategoryResp> getCategoryList();

    @Select("select * from category limit #{param.page},#{param.limit} ")
    @Results(id = "getBackCategoryMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "articleCount",
                    one = @One(select = "com.blog.mapper.ArticleMapper.countArticleByCategoryId"))
    })
    List<CategoryBackResp> getBackCategoryList(@Param("param") PageReq req);

}

