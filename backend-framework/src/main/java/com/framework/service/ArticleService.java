package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.Result;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.response.*;

import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2024-03-25 13:22:23
 */
public interface ArticleService extends IService<Article> {
    Result<PageResult<ArticleHomeResp>> getArticleList(Integer current, Integer size);

    Result<List<ArticleRecommendResp>> getArticleRecommendList();

    Result<ArticleResp> getArticleDetail(Integer articleId);

    Result<List<ArticleSearchResp>> getArticleSearchList(String keyword);

    Result<?> likeArticle(Integer articleId);
}

