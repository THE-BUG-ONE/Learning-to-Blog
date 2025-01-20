package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.Message;
import com.blog.entity.vo.request.MessageAddReq;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.MessageBackResp;
import com.blog.entity.vo.response.MessageResp;
import com.blog.entity.vo.response.PageResult;
import com.blog.mapper.MessageMapper;
import com.blog.service.MessageService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.PageUtils;
import com.blog.utils.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author Felz
* @description 针对表【message】的数据库操作Service实现
* @createDate 2025-01-14 14:45:14
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService{

    @Resource
    private PageUtils pageUtils;

    @Resource
    private WebUtils webUtils;

    @Override
    public PageResult<MessageResp> getMessageList(PageReq req) {
        pageUtils.setPage(req);
        List<MessageResp> respList = baseMapper.getMessageList(req);
        return new PageResult<>(respList.size(), respList);
    }

    @Override
    @Transactional
    public MessageResp addMessage(MessageAddReq req) {
        try {
            int userId = webUtils.getRequestUser().getId();
            Message message = new Message(
                    req.getParentId(), req.getRootId(), req.getFromUserId(), userId, req.getMessage());
            baseMapper.addMessage(message);
            return BeanCopyUtils.copyBean(baseMapper.getMessage(message.getId()), MessageResp.class);
        } catch (Exception e) {
            throw new RuntimeException("评论添加异常" + e.getMessage());
        }
    }

    @Override
    public List<MessageResp> getMessageReplyList(Integer rootId) {
        if (rootId == 0) return null;
        return baseMapper.getMessageReplyList(rootId);
    }

    @Override
    public PageResult<MessageBackResp> getBackMessageList(PageReq req) {
        pageUtils.setPage(req);
        List<MessageBackResp> respList = baseMapper.getMessageBackList(req);
        return new PageResult<>(respList.size(), respList);
    }

    @Override
    @Transactional
    public void deleteMessage(List<Integer> messageIdList) {
        //获取删除列表内的根留言 id 列表
        List<Integer> rootIdList = lambdaQuery()
                .select(Message::getId)
                .eq(Message::getRootId, SystemConstants.MESSAGE_ROOT)
                .list().stream().map(Message::getId).filter(messageIdList::contains).toList();
        //获取删除列表内的父留言 id 列表
        List<Integer> parentIdList = lambdaQuery()
                .select(Message::getId)
                .eq(Message::getParentId, SystemConstants.MESSAGE_PARENT)
                .list().stream().map(Message::getId).filter(messageIdList::contains).toList();

        //根据以上id列表获取关联留言id
        Set<Integer> validIdList = lambdaQuery()
                .select(Message::getId)
                .in(Message::getRootId, rootIdList).or()
                .in(Message::getParentId, parentIdList)
                .list().stream().map(Message::getId).collect(Collectors.toSet());
        //根据关联列表与总列表获取并集
        validIdList.addAll(messageIdList);

        try {
            if (!removeBatchByIds(validIdList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("留言删除异常:" + e.getMessage());
        }
    }
}




