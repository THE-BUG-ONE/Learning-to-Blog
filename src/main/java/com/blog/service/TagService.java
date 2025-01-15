package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Tag;
import com.blog.entity.vo.request.TagBackReq;
import com.blog.entity.vo.request.TagReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.TagBackResp;
import com.blog.entity.vo.response.TagOptionResp;
import com.blog.entity.vo.response.TagResp;

import java.util.List;

/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2024-03-28 16:19:20
 */
public interface TagService extends IService<Tag> {
    void addTag(List<String> tagNameList);

    List<TagResp> getTagList();

    void addTag(TagReq tagReq);

    void deleteTag(List<Integer> tagIdList);

    PageResult<TagBackResp> getBackTagList(TagBackReq tagBackReq);

    List<TagOptionResp> getTagOptionList(Integer articleId);

    void updateTag(TagReq tagReq);

    void cleanTag();
}

