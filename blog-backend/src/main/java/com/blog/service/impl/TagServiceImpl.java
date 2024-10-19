package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.Article;
import com.blog.entity.dao.ArticleTag;
import com.blog.entity.dao.Tag;
import com.blog.entity.vo.request.ArticleConditionReq;
import com.blog.entity.vo.request.TagBackReq;
import com.blog.entity.vo.request.TagReq;
import com.blog.entity.vo.response.*;
import com.blog.mapper.TagMapper;
import com.blog.service.ArticleService;
import com.blog.service.ArticleTagService;
import com.blog.service.CategoryService;
import com.blog.service.TagService;
import com.blog.utils.BeanCopyUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-03-28 16:19:20
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Lazy
    @Resource
    private ArticleService articleService;

    @Lazy
    @Resource
    private CategoryService categoryService;

    @Lazy
    @Resource
    private ArticleTagService articleTagService;

    @Override
    @Transactional
    public void addTag(List<String> tagNameList) {
        try {
            //创建标签列表
            List<Tag> tagList = tagNameList
                    .stream()
                    .map(tagName -> new Tag(tagName, DateTime.now()))
                    .toList();
            if (tagList.isEmpty() || !this.saveBatch(tagList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("标签添加异常");
        }
    }

    //分类id可为空，为空时从文章字段中获取
    @Override
    public ArticleConditionList getArticleConditionList(ArticleConditionReq articleConditionReq) {
        Integer categoryId = articleConditionReq.getCategoryId();
        Integer tagId = articleConditionReq.getTagId();
        Integer current = articleConditionReq.getCurrent();
        Integer size = articleConditionReq.getSize();

        Map<Integer, Integer> categoryIdMap = new HashMap<>();
        List<Article> articleList = articleService.getArticlePageList(current, size, categoryId, tagId)
                .stream()
                .peek(article -> categoryIdMap.put(article.getId(), article.getCategoryId()))
                .toList();

        List<ArticleConditionResp> articleConditionRespList =
                BeanCopyUtils.copyBeanList(articleList, ArticleConditionResp.class)
                        .stream()
                        .peek(article -> {
                            article.setTagVOList(this.getTagOptionList(article.getId()));
                            article.setCategory(categoryService.getCategoryOptionVO(
                                    categoryIdMap.get(article.getId())));})
                        .toList();
        return new ArticleConditionList(
                this.getById(tagId).getTagName(),
                articleConditionRespList);
    }

    @Override
    public List<TagResp> getTagList() {
        return BeanCopyUtils.copyBeanList(this.list(), TagResp.class)
                .stream()
                .peek(tagResp -> tagResp.setArticleCount(Math.toIntExact(
                        articleTagService.lambdaQuery()
                                .eq(ArticleTag::getTagId, tagResp.getId())
                                .count())))
                .toList();
    }

    @Override
    @Transactional
    public void addTag(TagReq tagReq) {
        try {
            if (!this.save(new Tag(tagReq.getTagName(), DateUtil.date())))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("标签添加异常");
        }
    }

    @Override
    @Transactional
    public void deleteTag(List<Integer> tagIdList) {
        try {

            if (!this.removeBatchByIds(tagIdList
                    .stream()
                    .filter(id -> !articleTagService.lambdaQuery()
                            .eq(ArticleTag::getTagId, id).exists())
                    .toList()))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("标签删除异常");
        }
    }

    @Override
    public PageResult<TagBackResp> getBackTagList(TagBackReq tagBackReq) {
        Integer current = tagBackReq.getCurrent();
        Integer size = tagBackReq.getSize();
        String keyword = tagBackReq.getKeyword();

        //获取标签列表，若关键词存在，匹配关键词
        List<Tag> tagList = this.page(new Page<>(current, size),
                this.lambdaQuery()
                        .like(keyword != null, Tag::getTagName, keyword)
                        .getWrapper())
                .getRecords();
        //填充标签对应的文章数
        List<TagBackResp> tagBackRespList =
                BeanCopyUtils.copyBeanList(tagList, TagBackResp.class)
                        .stream()
                        .peek(tagBackResp -> tagBackResp.setArticleCount(Math.toIntExact(
                                articleTagService.lambdaQuery()
                                        .eq(ArticleTag::getTagId, tagBackResp.getId())
                                        .count())))
                        .toList();

        return new PageResult<>(tagBackRespList.size(), tagBackRespList);
    }

    //获取文章标签列表（标签id，标签名）
    @Override
    public List<TagOptionResp> getTagOptionList(Integer articleId) {
        return baseMapper.getTagOptionList(articleId);
    }

    @Override
    @Transactional
    public void updateTag(TagReq tagReq) {
        try {
            Integer id = tagReq.getId();
            String tagName = tagReq.getTagName();
            if (id == null || !this.lambdaUpdate()
                    .eq(Tag::getId, id)
                    .set(Tag::getTagName, tagName)
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("标签修改异常");
        }
    }


}

