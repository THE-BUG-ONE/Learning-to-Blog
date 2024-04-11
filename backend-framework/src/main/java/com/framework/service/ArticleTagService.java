package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.ArticleTag;
import com.framework.entity.dao.Tag;

import java.util.List;

/**
 * (ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2024-03-25 15:32:36
 */
public interface ArticleTagService extends IService<ArticleTag> {
    void addArticleTag(Integer articleId, List<String> tagNameList);
}

