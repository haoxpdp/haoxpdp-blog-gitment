package com.zichan360.middle.beans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.util.List;

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
public class CustomerResponse extends WxBaseResponse {


    @JSONField(name = "external_userid")
    private List<String> externalUserId;

}
