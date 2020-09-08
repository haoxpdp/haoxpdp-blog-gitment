package com.zichan360.middle.api;

import cn.detach.api.annoation.ApiSupport;
import cn.detach.api.annoation.RemoteApi;
import com.zichan360.middle.beans.CustomerResponse;

/**
 * @author haoxp
 * @date 20/9/8
 */
@ApiSupport
public interface WxCustomerApi {

    @RemoteApi(
            url = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/list?access_token=${ACCESS_TOKEN}&userid=${USERID}"
    )
    CustomerResponse getCustomersByUserId(String token, String userId);

}
