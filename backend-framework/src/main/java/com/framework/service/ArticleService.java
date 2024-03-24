package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.RestBean;
import com.framework.entity.Article;

public interface ArticleService extends IService<Article> {

    String getHotArticleList();

    String getArticleList();
}
