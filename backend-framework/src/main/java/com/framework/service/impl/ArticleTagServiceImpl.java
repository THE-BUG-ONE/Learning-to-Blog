package com.framework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.entity.dao.ArticleTag;
import com.framework.entity.dao.Tag;
import com.framework.mapper.ArticleTagMapper;
import com.framework.service.ArticleTagService;
import com.framework.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2024-03-25 15:32:36
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Lazy
    @Resource
    private TagService tagService;

    @Override
    @Transactional
    public void addArticleTag(Integer articleId, List<String> tagNameList) {
        try {
            //获取文章对应的标签ID列表
            List<Integer> tagIdList = tagService.lambdaQuery()
                    .in(Tag::getTagName, tagNameList)
                    .list()
                    .stream()
                    .map(Tag::getId)
                    .toList();
            //创建文章标签列表
            List<ArticleTag> articleTagList = tagIdList
                    .stream()
                    .map(tagId -> new ArticleTag(articleId, tagId))
                    .toList();
            if (articleTagList.isEmpty() || !this.saveBatch(articleTagList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("文章标签表存入异常");
        }
    }
}

