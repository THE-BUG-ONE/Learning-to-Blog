package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.constants.SystemConstants;
import com.blog.entity.dao.Talk;
import com.blog.entity.dao.User;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.TalkResp;
import com.blog.mapper.TalkMapper;
import com.blog.service.TalkService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.PageUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        pageUtils.setPage(pageReq);
        Integer page = pageReq.getPage();
        Integer limit = pageReq.getLimit();

        List<Talk> talkList = page(new Page<>(page, limit), lambdaQuery()
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

