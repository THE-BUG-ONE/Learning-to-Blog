package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.RestBean;
import com.framework.entity.Article;
import com.framework.entity.vo.response.ArticleDetailVO;
import com.framework.entity.vo.response.ArticleRecommendVO;
import com.framework.entity.vo.response.ArticleRespVO;
import com.framework.entity.vo.response.ArticleSearchVO;

import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2024-03-25 13:22:23
 */
public interface ArticleService extends IService<Article> {
    RestBean<ArticleRespVO> getArticleList(Integer current, Integer size);

    RestBean<List<ArticleRecommendVO>> getArticleRecommendList();

    RestBean<ArticleDetailVO> getArticleDetail(Integer articleId);

    RestBean<List<ArticleSearchVO>> getArticleSearchList(String keyword);
}

