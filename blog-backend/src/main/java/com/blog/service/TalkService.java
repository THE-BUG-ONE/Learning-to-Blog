package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.entity.dao.Talk;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.PageResult;
import com.blog.entity.vo.response.TalkResp;

import java.util.List;

/**
 * (Talk)表服务接口
 *
 * @author makejava
 * @since 2024-05-23 15:01:57
 */
public interface TalkService extends IService<Talk> {

    List<String> getHomeTalk();

    PageResult<TalkResp> getTalkList(PageReq pageReq);
}

