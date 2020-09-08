package com.zichan360.middle.service;

import com.zichan360.middle.api.WxApi;
import com.zichan360.middle.api.WxCustomerApi;
import com.zichan360.middle.beans.CustomerResponse;
import com.zichan360.middle.beans.WxToken;
import com.zichan360.middle.mapper.CustomerMapper;
import com.zichan360.middle.util.WxTokenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haoxp
 * @date 20/9/8
 */
@Service
public class WxService {

    private static final Logger logger = LoggerFactory.getLogger(WxService.class);

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private WxApi wxApi;

    @Resource
    private WxCustomerApi customerApi;

    @Resource
    private WxTokenCache tokenCache;


    @Value("${encrypt.corpId}")
    private String corpId;
    @Value("${encrypt.customerSecret}")
    private String customerSecret;

    public String getToken() {
        WxToken token = tokenCache.getCustomerToken();
        if (token != null) {
            return token.getAccessToken();
        }
        WxToken wxToken = wxApi.getWxToken(corpId, customerSecret);
        if (!wxToken.isSuccess()) {
            throw new RuntimeException("获取微信token失败！");
        }
        tokenCache.setCustomerToken(wxToken);
        return wxToken.getAccessToken();
    }


    public List<String> getRemoteCustomerList(String userId) {
        CustomerResponse response = customerApi.getCustomersByUserId(getToken(), userId);
        if (!response.isSuccess()) {
            throw new RuntimeException("获取客户列表失败");
        }
        return response.getExternalUserId();
    }

}
