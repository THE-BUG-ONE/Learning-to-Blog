package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Category;
import com.blog.entity.vo.request.KeywordReq;
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

    @Select("select * from category where id = #{id}")
    CategoryOptionResp getCategoryOption(Integer id);

    /*[
            {
                "articleCount": 0,
                "categoryName": "string",
                "id": 0
            }
    ]*/
    @Select("select * from category")
    @Results(id = "getCategoryMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "articleCount",
                    one = @One(select = "com.blog.mapper.ArticleMapper.countArticleByCategoryId"))
    })
    List<CategoryResp> getCategoryList();

    /*{
            "articleCount": 0,
            "categoryName": "string",
            "createTime": "2024-04-13T05:47:43.625Z",
            "id": 0
    }*/
//    @Select("""
//    <script>
//        select * from category
//        <where>
//            <if test='param.keyword != null'>
//                and category_name like CONCAT('%',#{param.keyword},'%')
//            </if>
//        </where>
//        limit #{param.page},#{param.limit}
//    </script>
//    """)
//    @Results(id = "getBackCategoryMap", value = {
//            @Result(column = "id", property = "id"),
//            @Result(column = "id", property = "articleCount",
//                    one = @One(select = "com.blog.mapper.ArticleMapper.countArticleByCategoryId"))
//    })
//    List<CategoryBackResp> getBackCategoryList(@Param("param") KeywordReq param);

    @Select("select * from category limit #{param.page},#{param.limit} ")
    @Results(id = "getBackCategoryMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "articleCount",
                    one = @One(select = "com.blog.mapper.ArticleMapper.countArticleByCategoryId"))
    })
    List<CategoryBackResp> getBackCategoryList(@Param("param") PageReq req);

}
