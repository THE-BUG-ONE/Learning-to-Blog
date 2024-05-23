package com.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.framework.entity.dao.Talk;
import com.framework.entity.vo.response.TalkResp;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Talk)表数据库访问层
 *
 * @author makejava
 * @since 2024-05-23 15:01:57
 */
public interface TalkMapper extends BaseMapper<Talk> {
}

