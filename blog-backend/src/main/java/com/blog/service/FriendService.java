package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Friend;
import com.blog.entity.vo.request.FriendBackReq;
import com.blog.entity.vo.request.FriendReq;
import com.blog.entity.vo.response.FriendBackResp;
import com.blog.entity.vo.response.FriendResp;
import com.blog.entity.vo.response.PageResult;

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

