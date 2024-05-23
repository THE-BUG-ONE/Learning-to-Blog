package com.framework.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.constants.SystemConstants;
import com.framework.entity.dao.User;
import com.framework.entity.vo.request.PageReq;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.TalkResp;
import com.framework.mapper.TalkMapper;
import com.framework.entity.dao.Talk;
import com.framework.service.TalkService;
import com.framework.service.UserService;
import com.framework.utils.BeanCopyUtils;
import com.framework.utils.PageUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * (Talk)表服务实现类
 *
 * @author makejava
 * @since 2024-05-23 15:01:57
 */
@Service("talkService")
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements TalkService {

    @Resource
    private PageUtils pageUtils;

    @Lazy
    @Resource
    private UserService userService;

    @Override
    public List<String> getHomeTalk() {
        return lambdaQuery()
                .select(Talk::getTalkContent)
                .eq(Talk::getStatus, SystemConstants.TALK_IS_PUBLIC)
                .orderByDesc(Talk::getIsTop)
                .last(SystemConstants.LAST_LIMIT_10)
                .list()
                .stream()
                .map(Talk::getTalkContent)
                .toList();
    }

    @Override
    public PageResult<TalkResp> getTalkList(PageReq pageReq) {
        pageUtils.setCurrent(pageReq);
        Integer current = pageReq.getCurrent();
        Integer size = pageReq.getSize();

        List<Talk> talkList = page(new Page<>(current, size), lambdaQuery()
                .eq(Talk::getStatus, SystemConstants.TALK_IS_PUBLIC)
                .orderByDesc(Talk::getIsTop)
                .getWrapper()).getRecords();
        Map<Integer, Object> userMap = new HashMap<>();
        talkList.forEach(talk -> userMap.put(talk.getId(),userService
                .lambdaQuery()
                .eq(User::getId,talk.getUserId())
                .one()));
        List<TalkResp> respList = BeanCopyUtils.copyBeanList(talkList, TalkResp.class)
                .stream().peek(talk -> {
                    User user = (User) userMap.get(talk.getId());
                    talk
                            .setImgList(null)
                            .setAvatar(user.getAvatar())
                            .setNickname(user.getNickname())
                            .setCommentCount(null);
                })
                .toList();
        return new PageResult<>(respList.size(), respList);
    }
}

