package com.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.entity.dao.Message;
import com.blog.entity.vo.request.PageReq;
import com.blog.entity.vo.response.MessageResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author Felz
* @description 针对表【message】的数据库操作Mapper
* @createDate 2025-01-14 14:45:14
* @Entity com.blog.entity.dao.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    @Select("select * from message limit #{param.page},#{param.limit} ")
    @Results(id = "getMessageMap", value = {
            @Result(column = "user_id", property = "user",
                    one = @One(select = "com.blog.mapper.UserMapper.getUserRespById"))
    })
    List<MessageResp> getMessageList(@Param("param") PageReq req);
}




