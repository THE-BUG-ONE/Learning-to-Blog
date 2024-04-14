package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Tag;
import com.framework.entity.vo.request.ArticleConditionReq;
import com.framework.entity.vo.request.TagBackReq;
import com.framework.entity.vo.request.TagReq;
import com.framework.entity.vo.response.*;

import java.util.List;

/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2024-03-28 16:19:20
 */
public interface TagService extends IService<Tag> {
    void addTag(List<String> tagNameList);

    ArticleConditionList getArticleConditionList(ArticleConditionReq articleConditionReq);

    List<TagResp> getTagList();

    void addTag(TagReq tagReq);

    void deleteTag(List<Integer> tagIdList);

    PageResult<TagBackResp> getBackTagList(TagBackReq tagBackReq);

    List<TagOptionResp> getTagOptionList(Integer articleId);
}

