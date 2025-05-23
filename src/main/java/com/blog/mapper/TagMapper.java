package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Tag;
import com.blog.entity.vo.response.TagOptionResp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Tag)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-28 16:19:20
 */
public interface TagMapper extends BaseMapper<Tag> {

    @Select("select * from tag t left join article_tag at on t.id = at.tag_id where at.article_id = #{articleId}")
    List<TagOptionResp> getTagOptionList(Integer articleId);

    @Delete("delete from tag where id not in(select distinct tag_id from article_tag)")
    void cleanTag();
}

