package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

/**
 * @author haoxp
 * @date 20/8/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatEncrypt {
    private Long seq;

    @JSONField(name = "msgid")
    private String msgId;

    @JSONField(name = "publickey_ver")
    private String publicKeyVer;

    @JSONField(name="encrypt_random_key")
    private String encryptKey;

    @JSONField(name = "encrypt_chat_msg")
    private String encryptMst;
}
