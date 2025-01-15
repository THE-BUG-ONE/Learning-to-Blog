package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Notice;
import com.blog.entity.vo.response.NoticeResp;

import java.util.List;

/**
* @author Felz
* @description 针对表【notice】的数据库操作Service
* @createDate 2025-01-09 15:36:19
*/
public interface NoticeService extends IService<Notice> {

    List<NoticeResp> getSystemMsgList();
}
