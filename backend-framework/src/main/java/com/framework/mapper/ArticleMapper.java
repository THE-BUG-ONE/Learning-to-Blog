package com.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.response.ArticlePaginationResp;

/**
 * (Article)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-25 13:22:19
 */
public interface ArticleMapper extends BaseMapper<Article> {
    ArticlePaginationResp selectLastArticle(Integer articleId);
    ArticlePaginationResp selectNextArticle(Integer articleId);
}

