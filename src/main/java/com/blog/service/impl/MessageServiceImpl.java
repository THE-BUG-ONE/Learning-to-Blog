package com.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.Message;
import com.blog.entity.vo.request.CheckReq;
import com.blog.entity.vo.request.MessageBackReq;
import com.blog.entity.vo.request.MessageReq;
import com.blog.entity.vo.response.MessageBackResp;
import com.blog.entity.vo.response.MessageResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.mapper.MessageMapper;
import com.blog.service.MessageService;
import com.blog.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (Message)表服务实现类
 *
 * @author makejava
 * @since 2024-04-08 14:23:18
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public List<MessageResp> getMessageList() {
        return BeanCopyUtils.copyBeanList(this.lambdaQuery()
                .eq(Message::getIsCheck, SystemConstants.MESSAGE_IS_CHECKED)
                .list(), MessageResp.class);
    }

    @Override
    @Transactional
    public void addMessage(MessageReq messageReq) {
        try {
            //TODO 无法获取用户IP与用户地址
            if (!this.save(new Message(
                    null,
                    messageReq.getNickname(),
                    messageReq.getAvatar(),
                    messageReq.getMessageContent(),
                    "",
                    "",
                    null,
                    DateTime.now(),
                    null
            ))) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("留言添加异常");
        }
    }

    @Override
    @Transactional
    public void deleteMessage(List<Integer> messageIdList) {
        try {
            if (!this.removeBatchByIds(messageIdList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("留言删除异常");
        }
    }

    @Override
    public PageResult<MessageBackResp> getBackMessageList(MessageBackReq messageBackReq) {
        String keyword = messageBackReq.getKeyword();
        Integer page = messageBackReq.getPage();
        Integer limit = messageBackReq.getLimit();
        Integer isCheck = messageBackReq.getIsCheck();
        List<MessageBackResp> messageList = BeanCopyUtils.copyBeanList(this.page(new Page<>(page, limit),
                this.lambdaQuery()
                        .eq(isCheck != null, Message::getIsCheck, isCheck)
                        .like(keyword != null, Message::getMessageContent, keyword)
                        .getWrapper()).getRecords(), MessageBackResp.class);
        return new PageResult<>(messageList.size(), messageList);
    }

    @Override
    @Transactional
    public void checkMessage(CheckReq checkReq) {
        try {
            if (!this.lambdaUpdate()
                    .in(Message::getId, checkReq.getIdList())
                    .set(Message::getIsCheck, checkReq.getIsCheck())
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("留言审核异常");
        }
    }
}

