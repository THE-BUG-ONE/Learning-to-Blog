package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Article;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.*;

import java.util.List;
import java.util.Map;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2024-03-25 13:22:23
 */
public interface ArticleService extends IService<Article> {
    PageResult<ArticleHomeResp> getArticleHomeList(PageReq pageReq);

    List<ArticleRecommendResp> getArticleRecommendList();

    ArticleResp getArticle(Integer articleId);

    List<ArticleSearchResp> getArticleSearchList(String keyword);

    void likeArticle(Integer articleId);

    void addArticle(ArticleReq articleReq);

    void deleteArticle(List<Integer> articleIdList);

    ArticleInfoResp editArticle(Integer articleId);

    PageResult<ArticleBackResp> getBackArticle(ArticleBackReq articleBackReq);

    void recommendArticle(RecommendReq recommendReq);

    void recycleArticle(DeleteReq deleteReq);

    void topArticle(TopReq topReq);

    void updateArticle(ArticleReq articleReq);

    List<Article> getArticlePageList(Integer page, Integer limit, Integer categoryId, Integer tagId);

    ArticleDetailResp getArticleDetail(Integer id);

    Map<Integer, Long> getArticleLikeMap();

    Map<Integer, Long> getArticleViewMap();

    void updateArticleLikeAndViewCount();

    PageResult<ArticleHomeResp> getArticleHomeListByCategory(ArticleConditionReq req);
}

