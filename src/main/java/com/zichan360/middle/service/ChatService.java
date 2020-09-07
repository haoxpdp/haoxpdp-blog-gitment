package com.zichan360.middle.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.zichan360.middle.config.StringArrayTypeHandler;
import com.zichan360.middle.mapper.ChatMsgMapper;
import com.zichan360.middle.pojo.HlzxChatMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author haoxp
 * @date 20/9/7
 */
@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Resource
    private ChatMsgMapper mapper;

    public List<String> senderList() {
        return mapper.getSenderList();
    }

    public List<String> getReceiverList(String sender) {
        return mapper.getReceverList(sender).stream()
                .map(m -> {
                    String[] receiver = StringArrayTypeHandler.toObject((String) m.get("receiver")) ;
                    String roomId = (String) m.get("room_id");
                    return receiver.length == 1 ? receiver[0] : roomId;
                }).collect(Collectors.toList());
    }

}
