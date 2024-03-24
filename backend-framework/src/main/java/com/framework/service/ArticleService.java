package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.RestBean;
import com.framework.entity.Article;
import com.framework.entity.vo.HotArticleVO;

import java.util.List;

public interface ArticleService extends IService<Article> {

    RestBean<List<HotArticleVO>> getHotArticleList();

    RestBean<List<Article>> getArticleList();
}
