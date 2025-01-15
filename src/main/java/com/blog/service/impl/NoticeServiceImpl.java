package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.Notice;
import com.blog.entity.vo.response.NoticeResp;
import com.blog.mapper.NoticeMapper;
import com.blog.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Felz
* @description 针对表【notice】的数据库操作Service实现
* @createDate 2025-01-09 15:36:19
*/
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

    @Override
    public List<NoticeResp> getSystemMsgList() {
        return baseMapper.getNoticeList();
    }
}




