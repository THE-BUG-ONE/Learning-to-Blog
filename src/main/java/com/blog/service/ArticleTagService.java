package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.ArticleTag;

import java.util.List;

/**
 * (ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2024-03-25 15:32:36
 */
public interface ArticleTagService extends IService<ArticleTag> {
    void addArticleTag(Integer articleId, List<String> tagNameList);

    void cleanArticleTag(Integer articleId, List<Integer> abandonTagIdList);

    void cleanArticleTagList(List<Integer> articleIdList);
}

