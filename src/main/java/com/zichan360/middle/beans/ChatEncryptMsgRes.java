package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.util.List;

/**
 * @author haoxp
 * @date 20/8/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEncryptMsgRes {

    @JSONField(name = "errcode")
    private Integer errorCode;

    @JSONField(name = "errmsg")
    private String errMsg;

    @JSONField(name = "chatdata")
    private List<ChatEncrypt> chatData;
}
