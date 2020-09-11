package com.zichan360.middle.beans.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author haoxp
 * @date 20/9/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMsg {

    @JSONField(name = "md5sum")
    String md5Sum;

    @JSONField(name = "fileext")
    String fileExt;

    @JSONField(name = "filename")
    String fileName;

    @JSONField(name = "filesize")
    String fileSize;

    @JSONField(name = "sdkfileid")
    String sdkFileId;

}
