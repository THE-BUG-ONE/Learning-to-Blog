package com.framework.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.entity.vo.request.FriendBackReq;
import com.framework.entity.vo.request.FriendReq;
import com.framework.entity.vo.response.FriendBackResp;
import com.framework.entity.vo.response.FriendResp;
import com.framework.entity.vo.response.PageResult;
import com.framework.mapper.FriendMapper;
import com.framework.entity.dao.Friend;
import com.framework.service.FriendService;
import com.framework.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (Friend)表服务实现类
 *
 * @author makejava
 * @since 2024-04-15 13:50:16
 */
@Service("friendService")
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Override
    public List<FriendResp> getFriendList() {
        return BeanCopyUtils.copyBeanList(this.list(), FriendResp.class);
    }

    @Override
    @Transactional
    public void addFriend(FriendReq friendReq) {
        try {
            if (!this.save(BeanCopyUtils.copyBean(friendReq, Friend.class)
                    .setCreateTime(DateUtil.date())))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("友链添加异常");
        }
    }

    @Override
    @Transactional
    public void deleteFriend(List<Integer> friendIdList) {
        try {
            if (!this.removeBatchByIds(friendIdList))
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("友链删除异常");
        }
    }

    @Override
    public PageResult<FriendBackResp> getBackFriendList(FriendBackReq friendBackReq) {
        Integer current = friendBackReq.getCurrent();
        Integer size = friendBackReq.getSize();
        String keyword = friendBackReq.getKeyword();

        List<FriendBackResp> friendBackRespList =
                BeanCopyUtils.copyBeanList(this.page(new Page<>(current, size),
                                this.lambdaQuery()
                                        .like(Friend::getName, keyword)
                                        .getWrapper())
                        .getRecords(), FriendBackResp.class);
        return new PageResult<>(friendBackRespList.size(), friendBackRespList);
    }

    @Override
    @Transactional
    public void updateFriend(FriendReq friendReq) {
        try {
            Integer id = friendReq.getId();
            String name = friendReq.getName();
            String color = friendReq.getColor();
            String avatar = friendReq.getAvatar();
            String url = friendReq.getUrl();
            String introduction = friendReq.getIntroduction();
            if (id == null || !this.lambdaUpdate()
                    .eq(Friend::getId, id)
                    .set(Friend::getName, name)
                    .set(Friend::getColor, color)
                    .set(Friend::getAvatar, avatar)
                    .set(Friend::getUrl, url)
                    .set(Friend::getIntroduction, introduction)
                    .update())
                throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException("友链修改异常");
        }
    }
}

