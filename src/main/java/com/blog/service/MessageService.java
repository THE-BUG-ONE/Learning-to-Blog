package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Message;
import com.blog.entity.vo.request.MessageAddReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.MessageBackResp;
import com.blog.entity.vo.response.MessageResp;
import com.blog.entity.vo.response.PageResult;

import java.util.List;

/**
* @author Felz
* @description 针对表【message】的数据库操作Service
* @createDate 2025-01-14 14:45:14
*/
public interface MessageService extends IService<Message> {

    PageResult<MessageResp> getMessageList(PageReq req);

    MessageResp addMessage(MessageAddReq req);

    PageResult<MessageBackResp> getBackMessageList(PageReq req);

    void deleteMessage(List<Integer> messageIdList);
}
