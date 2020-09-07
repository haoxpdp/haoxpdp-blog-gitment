package com.zichan360.middle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zichan360.middle.pojo.HlzxChatMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author haoxp
 * @version v1.0
 * @date 20/9/4 14:12
 */
@Mapper
public interface ChatMsgMapper extends BaseMapper<HlzxChatMsg> {

    @Select("select DISTINCT sender from hlzx_chat_msg")
    List<String> getSenderList();

    @Select("select DISTINCT receiver,room_id from hlzx_chat_msg where sender = #{sender}")
    List<Map<String,Object>> getReceverList(@Param("sender") String sender);

}
