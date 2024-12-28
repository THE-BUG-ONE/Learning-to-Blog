package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Message;
import com.blog.entity.vo.request.CheckReq;
import com.blog.entity.vo.request.MessageBackReq;
import com.blog.entity.vo.request.MessageReq;
import com.blog.entity.vo.response.MessageBackResp;
import com.blog.entity.vo.response.MessageResp;
import com.blog.entity.vo.response.PageResult;

import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author makejava
 * @since 2024-04-08 14:23:18
 */
public interface MessageService extends IService<Message> {

    List<MessageResp> getMessageList();

    void addMessage(MessageReq messageReq);

    void deleteMessage(List<Integer> messageIdList);

    PageResult<MessageBackResp> getBackMessageList(MessageBackReq messageBackReq);

    void checkMessage(CheckReq checkReq);
}

