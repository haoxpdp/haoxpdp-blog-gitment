package com.zichan360.middle.beans.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author haoxp
 * @date 20/9/1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MixedMsg {

    private String type;
    private String content;

}
