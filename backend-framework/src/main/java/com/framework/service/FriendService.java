package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Friend;
import com.framework.entity.vo.request.FriendBackReq;
import com.framework.entity.vo.request.FriendReq;
import com.framework.entity.vo.response.FriendBackResp;
import com.framework.entity.vo.response.FriendResp;
import com.framework.entity.vo.response.PageResult;

import java.util.List;

/**
 * (Friend)表服务接口
 *
 * @author makejava
 * @since 2024-04-15 13:50:16
 */
public interface FriendService extends IService<Friend> {

    List<FriendResp> getFriendList();

    void addFriend(FriendReq friendReq);

    void deleteFriend(List<Integer> friendIdList);

    PageResult<FriendBackResp> getBackFriendList(FriendBackReq friendBackReq);

    void updateFriend(FriendReq friendReq);
}

