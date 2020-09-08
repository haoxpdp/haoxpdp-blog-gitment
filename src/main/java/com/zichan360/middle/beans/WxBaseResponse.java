package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author haoxp
 * @date 20/9/8
 */
@Data
public class WxBaseResponse {
    @JSONField(name = "errcode")
    private int errCode;
    @JSONField(name = "errmsg")
    private String errMsg;

    public boolean isSuccess() {
        return this.errCode == 0;
    }

}
