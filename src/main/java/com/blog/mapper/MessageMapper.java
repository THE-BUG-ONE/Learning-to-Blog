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
    @Results(id = "getMessageListMap", value = {
            @Result(column = "user_id", property = "user",
                    one = @One(select = "com.blog.mapper.UserMapper.getUserRespById")),
            @Result(column = "from_user_id", property = "fromUser",
                    one = @One(select = "com.blog.mapper.UserMapper.getUserOptionRespById"))
    })
    List<MessageResp> getMessageList(@Param("param") PageReq req);

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into message values (default, #{message.parentId}, #{message.rootId}, #{message.fromUserId}, #{message.userId}, #{message.message}, default, default, null)")
    void addMessage(@Param("message") Message message);

    @Select("select * from message where id = #{id}")
    @Results(id = "getMessageMap", value = {
            @Result(column = "user_id", property = "user",
                    one = @One(select = "com.blog.mapper.UserMapper.getUserRespById")),
            @Result(column = "from_user_id", property = "fromUser",
                    one = @One(select = "com.blog.mapper.UserMapper.getUserOptionRespById"))
    })
    MessageResp getMessage(int id);

    @Select("select * from message where root_id = #{rootId}")
    @Results(id = "getMessageReplyListMap", value = {
            @Result(column = "user_id", property = "user",
                    one = @One(select = "com.blog.mapper.UserMapper.getUserRespById")),
            @Result(column = "from_user_id", property = "fromUser",
                    one = @One(select = "com.blog.mapper.UserMapper.getUserOptionRespById"))
    })
    List<MessageResp> getMessageReplyList(Integer rootId);
}




