package com.zichan360.middle.web;

import cn.detachment.core.bean.Result;
import com.zichan360.middle.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author haoxp
 * @date 20/9/7
 */
@RestController
@RequestMapping("/audit")
public class WebController {


    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Resource
    private ChatService chatService;

    @RequestMapping("/senderList")
    public Result<?> senderList() {
        return Result.success(chatService.senderList());
    }

    @RequestMapping("/getReceiverBySender")
    public Result getReceiverBySender(String sender) {
        return Result.success(chatService.getReceiverList(sender));
    }

}
