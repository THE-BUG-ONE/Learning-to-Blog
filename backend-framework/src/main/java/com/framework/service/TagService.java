package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Tag;

import java.util.List;

/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2024-03-28 16:19:20
 */
public interface TagService extends IService<Tag> {
    void addTag(List<String> tagNameList);
}

