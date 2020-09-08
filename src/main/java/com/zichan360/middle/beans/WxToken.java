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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WxToken extends WxBaseResponse {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "expires_in")
    private int expiresIn;
}
