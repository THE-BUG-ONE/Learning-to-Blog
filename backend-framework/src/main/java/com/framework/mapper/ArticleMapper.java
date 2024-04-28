package com.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.request.ArticleConditionReq;
import com.framework.entity.vo.request.PageReq;
import com.framework.entity.vo.response.ArticleConditionResp;
import com.framework.entity.vo.response.ArticleHomeResp;
import com.framework.entity.vo.response.ArticleResp;
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
    @Select("select * from t_article limit #{param.current},#{param.size}")
    @Results(id = "articleHomeResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "category",
                    one = @One(select = "com.framework.mapper.CategoryMapper.getCategoryOption")),
            @Result(column = "id", property = "tagVOList",
                    many = @Many(select = "com.framework.mapper.TagMapper.getTagOptionList"))
    })
    List<ArticleHomeResp> getArticleHomeList(@Param("param") PageReq param);

    /*{
        "articleContent": "string",
        "articleCover": "string",
        "articleTitle": "string",
        "articleType": 0,
        "category": {
          "categoryName": "string",
          "id": 0
        },
        "createTime": "2024-03-28T07:00:47.751Z",
        "id": 0,
        "lastArticle": {
          "articleCover": "string",
          "articleTitle": "string",
          "id": 0
        },
        "likeCount": 0,
        "nextArticle": {
          "articleCover": "string",
          "articleTitle": "string",
          "id": 0
        },
        "tagVOList": [
          {
            "id": 0,
            "tagName": "string"
          }
        ],
        "updateTime": "2024-03-28T07:00:47.751Z",
        "viewCount": 0
    }*/
    @Select("select * from t_article where id = #{articleId}")
    @Results(id = "articleDetailResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "category",
                    one = @One(select = "com.framework.mapper.CategoryMapper.getCategoryOption")),
            @Result(column = "id", property = "tagVOList",
                    many = @Many(select = "com.framework.mapper.TagMapper.getTagOptionList"))
    })
    ArticleResp getArticleDetail(Integer articleId);

    @Select("select count(*) from t_article where category_id = #{categoryId}")
    Integer countArticleByCategoryId(Integer categoryId);

    /*{
        "articleConditionVOList": [{
            "articleCover": "string",
            "articleTitle": "string",
            "category": {
              "categoryName": "string",
              "id": 0
            },
            "createTime": "2024-03-29T06:40:20.925Z",
            "id": 0,
            "tagVOList": [
              {
                "id": 0,
                "tagName": "string"
              }
             ]
        }],
        "name": "string"
    }*/
    @Select("""
            select * from t_article
            where category_id = #{param.categoryId} and id
            in (select article_id from t_article_tag where tag_id = #{param.tagId})
            limit #{param.current},#{param.size}
            """)
    @Results(id = "getArticleConditionMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "category_id", property = "category",
                    one = @One(select = "com.framework.mapper.CategoryMapper.getCategoryOption")),
            @Result(column = "id", property = "tagVOList",
                    many = @Many(select = "com.framework.mapper.TagMapper.getTagOptionList"))
    })
    List<ArticleConditionResp> getArticleConditionList(@Param("param") ArticleConditionReq param);
}

