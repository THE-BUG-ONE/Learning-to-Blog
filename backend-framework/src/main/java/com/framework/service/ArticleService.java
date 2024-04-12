package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Article;
import com.framework.entity.vo.request.ArticleReq;
import com.framework.entity.vo.request.DeleteReq;
import com.framework.entity.vo.request.RecommendReq;
import com.framework.entity.vo.request.TopReq;
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

    void addArticle(ArticleReq articleReq);

    void deleteArticle(List<Integer> articleIdList);

    ArticleInfoResp editArticle(Integer articleId);

    PageResult<ArticleBackResp> getBackArticle(Integer articleType, Integer categoryId, Integer current, Integer isDelete, String keyword, Integer size, Integer status, Integer tagId);

    void recommendArticle(RecommendReq recommendReq);

    void recycleArticle(DeleteReq deleteReq);

    void topArticle(TopReq topReq);

    void updateArticle(ArticleReq articleReq);
}

