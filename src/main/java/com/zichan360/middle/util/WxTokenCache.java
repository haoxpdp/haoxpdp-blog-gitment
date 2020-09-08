package com.zichan360.middle.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zichan360.middle.beans.WxToken;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author haoxp
 * @date 20/9/8
 */
@Component
public class WxTokenCache implements InitializingBean {

    private Cache<String, WxToken> cache;

    public static final String customer_token_key = "hlzx_wx_customer_token";

    public void setCustomerToken(WxToken wxToken) {
        this.setToken(customer_token_key, wxToken);
    }

    public WxToken getCustomerToken() {
        return cache.getIfPresent(customer_token_key);
    }

    public void setToken(String key, WxToken token) {
        cache.put(key, token);
    }

    public WxToken getToken(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = CacheBuilder
                .newBuilder()
                .maximumSize(10)
                .expireAfterAccess(Duration.ofSeconds(7000))
                .build();
    }
}
