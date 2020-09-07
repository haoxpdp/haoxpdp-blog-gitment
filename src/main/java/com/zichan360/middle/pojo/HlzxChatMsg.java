package com.zichan360.middle.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author haoxp
 */
@Data
@TableName("hlzx_chat_msg")
public class HlzxChatMsg {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String msgId;
    private String action;
    private String originChatMsg;
    private LocalDateTime chatTime;
    private String msgType;
    private String sender;
    private String receiverSim;
    private String[] receiver;
    private String roomId;
    private Integer optimistic;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String content;

}
