package com.zichan360.middle.util;

import com.tencent.wework.Finance;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author haoxp
 * @date 20/9/1
 */
public class SdkHandler {

    private String corpId;
    private String secret;
    private String privateKey;

    private Long sdk;

    private Set<Long> caches = new HashSet<>();

    public void refresh() throws InterruptedException {
        synchronized (caches) {
            if (!CollectionUtils.isEmpty(caches)) {
                System.out.println("refresh wait!");
                caches.wait();
            }
            System.out.println("sdk destroy");
            Finance.DestroySdk(sdk);
            this.sdk = Finance.NewSdk();
        }
    }

    public void releaseSlice(String sliceKey) {
//        caches.
    }


}
