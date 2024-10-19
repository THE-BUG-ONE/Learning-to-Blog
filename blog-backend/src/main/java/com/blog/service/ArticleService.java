package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Article;
import com.blog.entity.vo.request.*;
import com.blog.entity.vo.response.*;

import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2024-03-25 13:22:23
 */
public interface ArticleService extends IService<Article> {
    PageResult<ArticleHomeResp> getArticleHomeList(PageReq pageReq);

    List<ArticleRecommendResp> getArticleRecommendList();

    ArticleResp getArticleDetail(Integer articleId);

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

    List<Article> getArticlePageList(Integer current, Integer size, Integer categoryId, Integer tagId);

}

