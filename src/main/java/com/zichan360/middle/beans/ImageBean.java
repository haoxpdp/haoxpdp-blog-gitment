package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

/**
 * @author haoxp
 * @date 20/9/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageBean {

    @JSONField(name = "md5sum")
    String md5Sum;
    @JSONField(name = "filesize")
    long fileSize;
    @JSONField(name = "sdkfileid")
    String sdkFileId;
}
