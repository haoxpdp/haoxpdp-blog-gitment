package com.zichan360.middle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tencent.wework.Finance;
import com.zichan360.middle.mapper.ChatMsgMapper;
import com.zichan360.middle.pojo.HlzxChatMsg;
import com.zichan360.middle.service.MsgService;
import com.zichan360.middle.util.RsaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author haoxp
 * @date 20/8/31
 */
@RestController
@SpringBootApplication
@RequestMapping("/audit")
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Value("${encrypt.rsa}")
    private String rsa;

    @Resource
    private ChatMsgMapper chatMsgMapper;

    @Resource
    private MsgService msgService;


    @RequestMapping("/status")
    private String status() {
        return "running";
    }

    @Override
    public void run(String... args) throws Exception {
        String id = "1302819186472402946";
    }
}
