package com.zichan360.middle.beans.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

/**
 * @author haoxp
 * @date 20/9/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceMsg {

    @JSONField(name = "md5num")
    private String md5Sum;

    @JSONField(name = "sdkfileid")
    private String sdkFileId;

    @JSONField(name = "voicesize")
    private String voiceSize;

    @JSONField(name = "playlength")
    private String playLength;

}
