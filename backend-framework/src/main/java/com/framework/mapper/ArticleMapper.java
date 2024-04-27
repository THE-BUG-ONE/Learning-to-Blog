package com.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.response.ArticleHomeResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-25 13:22:19
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /*{
        "count": 0, 文章数
        "recordList": [ 文章列表
        {
            "articleContent": "string",   文章内容1
            "articleCover": "string", 文章缩略图1
            "articleTitle": "string", 文章标题1
            "category": { 分类
            "categoryName": "string",
                    "id": 0
            },
            "createTime": "2024-03-25T05:39:38.535Z", 创建时间1
            "id": 0, 文章id1
            "isTop": 0, 文章置顶1
            "tagVOList": [    文章标签
            {
                "id": 0,
                    "tagName": "string"
            }
            ]
         }]
    }*/
    @Select("select * from t_article limit #{current},#{size}")
    @Results(id = "articleHomeResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(property = "category", column = "category_id",
                    one = @One(select = "com.framework.mapper.CategoryMapper.getCategoryOption")),
            @Result(property = "tagVOList", column = "id",
                    many = @Many(select = "com.framework.mapper.TagMapper.getTagOptionList"))
    })
    List<ArticleHomeResp> getArticleHomeList(@Param("current") Integer current,@Param("size") Integer size);
}

