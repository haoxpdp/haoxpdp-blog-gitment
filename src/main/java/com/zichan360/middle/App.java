package com.zichan360.middle;

import cn.detach.api.annoation.RemoteApiScanner;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wework.Finance;
import com.zichan360.middle.api.WxApi;
import com.zichan360.middle.beans.ChatMsg;
import com.zichan360.middle.mapper.ChatMsgMapper;
import com.zichan360.middle.pojo.HlzxChatMsg;
import com.zichan360.middle.service.MsgService;
import com.zichan360.middle.service.WxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;

import static com.zichan360.middle.service.MsgService.*;

/**
 * @author haoxp
 * @date 20/8/31
 */
@RestController
@SpringBootApplication
@RequestMapping("/audit")
@RemoteApiScanner(basePackages = "com.zichan360.middle.api")
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

    @Resource
    private WxService wxService;

    @Override
    public void run(String... args) throws Exception {
//        testDownVoice();
//        getMsg();
        wxService.getRemoteCustomerList("haoxueping");
    }

    public void getMsg() {
        int seq = 0;
        int len = msgService.get(seq).size();

        while (len >= 1000) {
            seq += 1000;
            len = msgService.get(seq).size();
        }
    }

    public void testDownVoice() {
        String id = "1302901081709248513";
        HlzxChatMsg msg = chatMsgMapper.selectById(id);
        ChatMsg chatMsg = JSONObject.parseObject(msg.getOriginChatMsg(), ChatMsg.class);

        Long sdk = Finance.NewSdk();
        Finance.Init(sdk, corpId, secret);
        String indexBuf = "";
        while (true) {
            long media_data = Finance.NewMediaData();
            int ret = Finance.GetMediaData(sdk, indexBuf, chatMsg.getMeetingVoiceCall().getSdkFileId(), null, null, 30000, media_data);
            if (ret != 0) {
                System.out.println(ret);
                return;
            }
            System.out.printf("getmediadata outindex len:%d, data_len:%d, is_finis:%d\n", Finance.GetIndexLen(media_data), Finance.GetDataLen(media_data), Finance.IsMediaDataFinish(media_data));
            try {
                FileOutputStream outputStream = new FileOutputStream(new File("H:\\desk\\media_data"));
                outputStream.write(Finance.GetData(media_data));
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Finance.IsMediaDataFinish(media_data) == 1) {
                Finance.FreeMediaData(media_data);
                break;
            } else {
                indexBuf = Finance.GetOutIndexBuf(media_data);
                Finance.FreeMediaData(media_data);
            }

            Finance.FreeSlice(media_data);
        }


        Finance.DestroySdk(sdk);
    }
}
