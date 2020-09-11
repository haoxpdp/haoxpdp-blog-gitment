package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import com.zichan360.middle.beans.msg.*;
import com.zichan360.middle.pojo.HlzxChatMsg;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
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

    private static final Logger logger = LoggerFactory.getLogger(ChatMsg.class);

    @JSONField(name = "msgid")
    private String msgId;

    private String action;

    private String from;

    @JSONField(name = "tolist")
    private List<String> toList;

    @JSONField(name = "roomid")
    private String roomId;

    @JSONField(name = "msgtime")
    private String msgTime;

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
    private MeetingVoiceMsg meetingVoiceCall;

    @JSONField(name = "voiceid")
    private String voiceId;

    private VoiceMsg voice;

    private ImageMsg image;

    private FileMsg file;


    private String originData;

    public HlzxChatMsg transToHlzxMsg() {
        HlzxChatMsg cm = new HlzxChatMsg();
        cm.setMsgId(this.getMsgId());
        cm.setSender(getFrom());
        cm.setAction(this.getAction());
        try {

            cm.setReceiver(getToList().toArray(new String[0]));
        } catch (Exception e) {
            logger.error(originData);
            e.printStackTrace();
        }
        String sim = cm.getReceiver().length > 1 ? cm.getRoomId() : cm.getReceiver()[0];
        cm.setReceiverSim(sim);
        cm.setOriginChatMsg(getOriginData());
        if (this.getMsgType().equals("meeting_voice_call")) {
            this.setMsgTime(getMsgTime() + "000");
        }

        cm.setChatTime(new Date(Long.parseLong(this.getMsgTime())));
        cm.setMsgType(this.getMsgType());
        cm.setRoomId(this.getRoomId());
        return cm;
    }

}
