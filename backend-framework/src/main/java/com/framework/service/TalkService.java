package com.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.entity.dao.Talk;
import com.framework.entity.vo.request.PageReq;
import com.framework.entity.vo.response.PageResult;
import com.framework.entity.vo.response.TalkResp;

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

