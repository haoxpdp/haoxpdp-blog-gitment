package com.zichan360.middle.api;

import cn.detach.api.annoation.ApiSupport;
import cn.detach.api.annoation.RemoteApi;
import com.zichan360.middle.beans.WxToken;

/**
 * @author haoxp
 * @date 20/9/7
 */
@ApiSupport
public interface WxApi {


    @RemoteApi(
            url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=${ID}&corpsecret=${SECRET}"
    )
    WxToken getWxToken(String corpId, String secret);

}
