package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.request.ArticleReq;
import com.framework.entity.vo.response.*;

import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2024-03-25 13:22:23
 */
public interface ArticleService extends IService<Article> {
    PageResult<ArticleHomeResp> getArticleHomeList(Integer current, Integer size);

    List<ArticleRecommendResp> getArticleRecommendList();

    ArticleResp getArticleDetail(Integer articleId);

    List<ArticleSearchResp> getArticleSearchList(String keyword);

    void likeArticle(Integer articleId);

    void addArticle(ArticleReq article);

    void deleteArticle(List<Integer> articleIdList);
}

