package com.zichan360.middle.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.zichan360.middle.beans.ChatMsg;
import com.zichan360.middle.config.StringArrayTypeHandler;
import com.zichan360.middle.mapper.ChatMsgMapper;
import com.zichan360.middle.pojo.HlzxChatMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
        return mapper.getReceverList(sender);
    }

    public List<HlzxChatMsg> getRecords(String receiver, String sender) {
        List<HlzxChatMsg> records = mapper.records(sender, receiver);
        records.forEach((HlzxChatMsg r) -> {
            ChatMsg chatMsg = JSONObject.parseObject(r.getOriginChatMsg(), ChatMsg.class);
            if (chatMsg.getMsgType().equals("text")) {
                r.setContent(chatMsg.getText().getContent());
            }
            r.setMsgTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(r.getChatTime()));
        });
        return records;
    }

}
