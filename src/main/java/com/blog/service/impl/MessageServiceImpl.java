package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.entity.dao.Message;
import com.blog.entity.vo.request.MessageAddReq;
import com.blog.entity.vo.request.PageReq;
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
            Message message = new Message(req.getParentId(), userId, req.getMessage());
            baseMapper.addMessage(message);
            return BeanCopyUtils.copyBean(baseMapper.getMessage(message.getId()), MessageResp.class);
        } catch (Exception e) {
            throw new RuntimeException("评论添加异常" + e.getMessage());
        }
    }
}




