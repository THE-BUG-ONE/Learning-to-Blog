package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.ArticleTag;
import com.blog.entity.dao.Tag;
import com.blog.mapper.ArticleTagMapper;
import com.blog.service.ArticleTagService;
import com.blog.service.TagService;
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
            if (tagNameList.isEmpty()) return;
            //保存新增标签
            tagService.addTag(tagNameList);
            //获取标签ID列表
            List<Integer> tagIdList = tagService.lambdaQuery()
                    .select(Tag::getId)
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
            throw new RuntimeException(e.getMessage()== null ? e.getMessage() :"文章标签表存入异常");
        }
    }

    @Override
    @Transactional
    public void cleanArticleTag(Integer articleId, List<Integer> abandonTagIdList) {
        try{
            if (abandonTagIdList.isEmpty()) return;

            //清除文章标签表弃用标签
            if (!removeBatchByIds(lambdaQuery()
                    .eq(ArticleTag::getArticleId, articleId)
                    .in(ArticleTag::getTagId, abandonTagIdList)
                    .list().stream().map(ArticleTag::getId).toList()))
                throw new RuntimeException();

            //清除文章标签表中不存在而标签表存在的标签
            tagService.deleteTag(abandonTagIdList);
        } catch (Exception e){
            throw new RuntimeException(e.getMessage()== null ? e.getMessage() :"文章标签表清除异常");
        }
    }

    @Override
    @Transactional
    public void cleanArticleTagList(List<Integer> articleIdList) {
        try {
            //数据库已设置外键文章表删除时对应数据删除
            //清除文章标签表中不存在而标签表存在的标签
            tagService.cleanTag();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage()== null ? e.getMessage() :"批量文章标签表清除异常");
        }
    }
}

