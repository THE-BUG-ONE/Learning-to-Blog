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
    public void addArticleTag(Integer articleId, List<String> tagNameList) {
        try {
            if (!tagNameList.isEmpty()){
                //获取文章tagId列表
                List<Integer> tagIdList = tagService.lambdaQuery()
                        .in(Tag::getTagName, tagNameList).list().stream()
                        .map(Tag::getId).toList();

                tagIdList.forEach(tagId -> this.save(new ArticleTag(articleId, tagId)));
            }
        } catch (Exception e) {
            throw new RuntimeException("文章标签表存入异常");
        }
    }
}

