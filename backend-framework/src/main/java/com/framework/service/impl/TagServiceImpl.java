package com.framework.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.entity.dao.ArticleTag;
import com.framework.mapper.TagMapper;
import com.framework.entity.dao.Tag;
import com.framework.service.ArticleTagService;
import com.framework.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2024-03-28 16:19:20
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTag(List<String> tagNameList) {
        try {
            if (!tagNameList.isEmpty())
                tagNameList.forEach(tagName -> this.save(new Tag(tagName, DateUtil.date())));
        } catch (Exception e) {
            throw new RuntimeException("标签保存异常");
        }
    }
}

