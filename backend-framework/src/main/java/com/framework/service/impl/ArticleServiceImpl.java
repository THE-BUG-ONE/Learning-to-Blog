package com.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.RestBean;
import com.framework.constants.SystemConstants;
import com.framework.entity.Article;
import com.framework.entity.vo.HotArticleVO;
import com.framework.mapper.ArticleMapper;
import com.framework.service.ArticleService;
import com.framework.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public RestBean<List<HotArticleVO>> getHotArticleList() {
        List<Article> articleList = this.lambdaQuery()
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .eq(Article::getIsRecommend, SystemConstants.ARTICLE_IS_RECOMMEND)
                .last(SystemConstants.ARTICLE_RECOMMEND_NUM)
                .list();
        List<HotArticleVO> articleVOList = BeanCopyUtils.copyBeanList(articleList, HotArticleVO.class);
        return RestBean.success(articleVOList);
    }

    @Override
    public RestBean<List<Article>> getArticleList() {
        List<Article> articleList = this.lambdaQuery()
                .eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL)
                .list();
        return RestBean.success(articleList);
    }

}
