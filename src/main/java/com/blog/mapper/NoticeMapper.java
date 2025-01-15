package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Notice;
import com.blog.entity.vo.response.NoticeResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Felz
* @description 针对表【notice】的数据库操作Mapper
* @createDate 2025-01-09 15:36:19
* @Entity com.blog.entity.dao.Notice
*/
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    @Select("select * from notice")
    List<NoticeResp> getNoticeList();
}




