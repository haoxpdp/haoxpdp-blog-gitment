package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author haoxp
 * @date 20/9/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingVoiceCall {
    @JSONField(name = "endtime")
    private LocalDateTime endTime;

    @JSONField(name = "sdkfileid")
    private String sdkFileId;
}
