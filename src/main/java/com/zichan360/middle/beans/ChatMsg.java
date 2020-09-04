package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author haoxp
 * @date 20/8/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsg {
    @JSONField(name = "msgid")
    private String msgId;

    private String action;

    private String from;

    @JSONField(name = "tolist")
    private List<String> toList;

    @JSONField(name = "roomid")
    private String roomId;

    @JSONField(name = "msgtime")
    private LocalDateTime msgTime;

    @JSONField(name = "msgtype")
    private String msgType;

    /**
     * 文本消息
     */
    private ChatContent text;

    /**
     * 混合消息
     */
    private List<MixedMsg> mixed;

    /**
     * 语音通话
     */
    @JSONField(name = "meeting_voice_call")
    private MeetingVoiceCall meetingVoiceCall;

    @JSONField(name = "voiceid")
    private String voiceId;

}
