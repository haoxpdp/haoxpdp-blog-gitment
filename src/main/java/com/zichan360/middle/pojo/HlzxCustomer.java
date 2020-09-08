package com.zichan360.middle.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author haoxp
 */
@Data
@TableName("hlzx_customer")
public class HlzxCustomer {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    private String userName;
    private String mobile;
    private String externalId;
    private Integer optimistic;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime createTime;

}
