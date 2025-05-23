package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.ArticleTag;
import com.blog.entity.dao.Tag;
import com.blog.entity.vo.request.TagBackReq;
import com.blog.entity.vo.request.TagReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.TagBackResp;
import com.blog.entity.vo.response.TagOptionResp;
import com.blog.entity.vo.response.TagResp;
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

import java.util.List;

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
            if (tagNameList.isEmpty()) return;
            //过滤数据库已有标签
            tagNameList = tagNameList.stream()
                    .filter(tagName -> !lambdaQuery().eq(Tag::getTagName, tagName).exists())
                    .toList();
            //创建标签列表
            List<Tag> tagList = tagNameList
                    .stream()
                    .map(tagName -> new Tag(tagName, DateTime.now()))
                    .toList();
            if (tagList.isEmpty() || !saveBatch(tagList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("标签添加异常");
        }
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
            //清除文章标签表中不存在而标签表存在的标签
            if (!removeBatchByIds(tagIdList
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
        Integer page = tagBackReq.getPage();
        Integer limit = tagBackReq.getLimit();
        String keyword = tagBackReq.getKeyword();

        //获取标签列表，若关键词存在，匹配关键词
        List<Tag> tagList = this.page(new Page<>(page, limit),
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

    @Override
    @Transactional
    public void cleanTag() {
        baseMapper.cleanTag();
    }

}

