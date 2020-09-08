package com.zichan360.middle.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.protobuf.ServiceException;
import com.tencent.wework.Finance;
import com.zichan360.middle.beans.ChatEncryptMsgRes;
import com.zichan360.middle.beans.ChatMsg;
import com.zichan360.middle.mapper.ChatMsgMapper;
import com.zichan360.middle.pojo.HlzxChatMsg;
import com.zichan360.middle.util.RsaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author haoxp
 * @date 20/8/31
 */
@Service
public class MsgService {

    private static final Logger logger = LoggerFactory.getLogger(MsgService.class);

    @Resource
    private ChatMsgMapper chatMsgMapper;

    public static final String rsa =
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDgwKfp3nrs6RBM\n" +
                    "QpgsN1pZZ+0g6COYInmBSeT7iqGGaN/dhS3L8uJ5M/2qkvDlPoGBu062gSMop/5A\n" +
                    "XfICK367q9CVtEOSrySJmj1Cdz9PjIaBPlZNqy8lyQgyRZw1vV4jaZdnuiBRLikk\n" +
                    "gURzBs3Bj4n6dnsWlrM0+WstH3/TDFWYesYVuD9LyM5qTrCKBuwNX/4swZxuqw+q\n" +
                    "ee0/s22vvwL/ybHuOX7QLRs26gMziK9kg0Gszc2lKC4cIRaptdnVmU8+NLLWTKaX\n" +
                    "J7abHCiWfLvQaWm48zsnqrb6mxGfkASNle5y1VjsBWFhr2bI+iNAtGCj3FLCGD02\n" +
                    "0SpTCLfzAgMBAAECggEBAN6kuzJoXOm375c8GTckFnKfCz8f97LL5AX5x8aPjHd0\n" +
                    "COCO/9Eb6NIceNuy+MR/6Rc1EEKnJscttCbbadRQdhk/mT/T9WSn79AIZUskiikp\n" +
                    "kk0SDbifLfOGdIIohxiNYWY/QedVcJI64jLxg3dXdBidMxhUqpaz7w85TbzDiYLs\n" +
                    "x7f3QOqubxDbIoKpHCUKu5QO6j+Ez4weU5+6dw1PLUaR/HBxtS8fIfeSGMa3Gw7x\n" +
                    "Ea9nua/NG0q/M2ZN7UGYhyHEoyXuqcVtXNy0vg7Xj81/d0KHAus1wXHYlXQQFW7a\n" +
                    "7d60zDTd5NbSTSvAFA0mJPs8pq/VPG8oLto1AriUYgECgYEA9X0xsTnsN8PywrLj\n" +
                    "NMavUetSEvSFfHIoY/4lCkJsL0DjXXtPdhdD/asxmE6v3Ay+t9VM0TfDSncxTGg0\n" +
                    "3HoAPu2uiLK7D1AfblZxP1c80ZRBT3u5K8G1J3ewX5naWq1X0g/Z6XnrEdupE7mT\n" +
                    "3oB8L9Cbm00AgjQIwTj+p75pjoECgYEA6mArUpnyvqO4uge0a+UhxGSfFRQyoifI\n" +
                    "lZ1e6kLyG6eytlRetC7DyM4nMVPAKMioEaFEVK7mfbDyiybUf7byLXUdqa2r2DyD\n" +
                    "aK7M5k6dfGrlLrrzPTU0AuQXTYqDVj1V/z0sgdrF4hFpr8NbpD5TXy8jVsMppvnx\n" +
                    "nlnXWUHrtHMCgYAr/45Cd2Ew9XuzDaDPOrT9d7G0GAM8tLEsisZUumbtaTHW9Vze\n" +
                    "i3jyJBa1d5TpWZdtF1u3O8lStV4ulZaz1WHp5WucsHv6WJ/phLCza41I+sth1Yjw\n" +
                    "oL05H6iK2eZaziqpSZIC2IkbIEWDdYVdIaYgY8Ef6oMm0vCmwPJTWjtNAQKBgQCZ\n" +
                    "LzXhjQEdHMDVxcdkZaQGSoW3LzZQyZPORccNcafv71Fc3hPvzEjma+ct1EFZ5pwC\n" +
                    "udaBSrcXG5nK3RwyRQ5QK6WxivMwJ4zz1JvTQzqnYO5d9o6YSLev1AhvA8MHkJsu\n" +
                    "cXsvfB+tvBfJavPwi1POeG6ufGwy2FXVW37mtbEqhQKBgAPFVcRH9Jj7ui/bymdD\n" +
                    "FJEauJTXcTvecx7/G0RW4pnxaEO75ds6HGTAicGa4VE/Z9LzauEnQorii2V0YaqX\n" +
                    "RLWja0mwOyab3IRW7CzR9IgfN4e5Q/H4Uy6VQ0lmlB18MFhkRDQed80znBZcXwOR\n" +
                    "z7XlcwS/e8X23HnqwyLokVRW";
    public static final String corpId = "ww33e37c528e3eae7d";
    public static final String secret = "yDaisJ5dSIF5j9uZzS6_xKHTvrzl0C-SfLcxr-KIbgo";


    public List<ChatMsg> get(int sequence) {
        Long sdk = Finance.NewSdk();
        Finance.Init(sdk, corpId, secret);
        Long chatSlice = Finance.NewSlice();
        int ret = Finance.GetChatData(sdk, sequence, 1000, null, null, 1000, chatSlice);
        if (ret != 0) {
            System.out.println("failer");
        }
        String response = Finance.GetContentFromSlice(chatSlice);
        ChatEncryptMsgRes res = JSONObject.parseObject(response, ChatEncryptMsgRes.class);
        List<ChatMsg> chatMsgList = res.getChatData()
                .stream()
                .map(chat -> {
                    String encryptKey = chat.getEncryptKey();
                    Long msg = Finance.NewSlice();
                    Finance.DecryptData(sdk, RsaUtil.dencrypt(encryptKey, rsa), chat.getEncryptMst(), msg);
                    String orign = Finance.GetContentFromSlice(msg);
                    ChatMsg chatMsg = JSONObject.parseObject(orign, ChatMsg.class);
                    chatMsg.setOriginData(orign);
                    Finance.FreeSlice(msg);
                    return chatMsg;
                })
                .filter(c -> !"switch".equals(c.getAction()))
                .peek(c -> {
                    try {
                        QueryWrapper<HlzxChatMsg> queryWrapper = new QueryWrapper<>();
                        queryWrapper.lambda().eq(HlzxChatMsg::getMsgId, c.getMsgId());
                        if (chatMsgMapper.selectCount(queryWrapper) == 0) {
                            chatMsgMapper.insert(c.transToHlzxMsg());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(c.getOriginData());
                    }
                })
                .collect(Collectors.toList());

        Finance.FreeSlice(chatSlice);
        Finance.DestroySdk(sdk);
        return chatMsgList;
    }


    public String downLoad(String id) {
        HlzxChatMsg msg = chatMsgMapper.selectById(id);
        ChatMsg chatMsg = JSONObject.parseObject(msg.getOriginChatMsg(), ChatMsg.class);
        String defaultSuffix = ".mp3";
        if ("image".equals(chatMsg.getMsgType())) {
            defaultSuffix = ".png";
        }
        String filePath = "D:\\tmp\\" + chatMsg.getMsgId() + defaultSuffix;
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }

        Long sdk = Finance.NewSdk();
        Finance.Init(sdk, corpId, secret);
        String indexBuf = "";
        while (true) {
            long media_data = Finance.NewMediaData();
            int ret = Finance.GetMediaData(sdk, indexBuf, sdkFileIdMap.get(chatMsg.getMsgType()).getSdkFileId(chatMsg), null, null, 30000, media_data);
            if (ret != 0) {
                System.out.println(ret);
                throw new RuntimeException("下载失败 ! " + ret);
            }
            System.out.printf("getmediadata outindex len:%d, data_len:%d, is_finis:%d\n", Finance.GetIndexLen(media_data), Finance.GetDataLen(media_data), Finance.IsMediaDataFinish(media_data));
            try {
                FileOutputStream outputStream = new FileOutputStream(new File(filePath));
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
        return filePath;
    }

    public static void getMsg() {
        String encryptKey = "JL+Se6tGLMZ6+jKriZgi2bW3v1Zr92LL4jc8EEhFiE2njztSMbjEO8XgThzCTxCaT0E7qmaJp4HXadsOlWU09DnRSdAlfxpoKMV/g5K5LJh/GsTqV8aTx7Q9ZBipsKk04iVpnnOyGt59X465xqFNf1amwIgxb2TAt573ll0pK22hngQA5wQTGTJfTXPL69cuKQuSjMjzkPoohgHCA6hIZ9vkhZKFihJTnPUwDb6EXnZn6qYPnYR+ZgkvVCwzcACXZm/dblJamaTsvHD1r8+Knf5Z94uHOMPFxzsLo9Orf0xlEWcd0XEYuZ71ck5CWmpvEeWV9dluA7DukcBhN0c2mw==";
        String result = "8HtkwcOJwG8c0TmJWpAQMLuF5YC7mVlpitpjQYTB2hIzs+wam9IsGSfQvWbunshO6tFzgbT4cIQIhwOInhhblQ==";
        String v = "9SuVDukT+Ld1XYGyvjW2kE8MjwdszecdWDYCFGVoPipwfRiIexTnakr4QEbcvAZJ2Y9Nx7zQbVR3PUCoxKEQNDlAibDWaB/myaD37VwgwkkfeB42M7YwKQWRi4MTvCGOSPu2Rk1Z7WlgucK6yfEMjIjKDM7ol5A7USSKyG5RGOPy5ErI4FZaZm1rM5n1FAx8R6cFiRxnv7TAji/zgWc9BMS1C8vJiysuyMuIJ9Ob0Rf0Wj4/toANkB+NrTjUdqQQUvM8IzGioPrPropCrKpHoPTW+FoCTbGHKoCo7Y1wlB7HbpFrSLOWjdKJvZXnvF2bI4Mcn9PyyitqRVQQaODhwos65lJItRGs+peqgYlvSbcWzsW/M+8vtv+A90MdiBKxArkKvyh5YyupPb0bxD1L4/WjSbTtFkciyA6Vlkf+Qnaq793H1NBwN1N0EFkRPZa/LthGx5Onkq56gvbTGEhgrlAzGUgc79RV9RUSKwXwQsFMH3AsO5YYN80q1nN6ejYfjx6fC4AJHLV39IUNhopNKW/UiOT607rASbzLkYOcAGVAr8x43zvXdI0Po3NwZ/ZBbVxgN+1gSCvQoPrbi/pe0MOk03GDgE1qHovg0xCS5wmuc89G+skRTy7huBREFQHWc2UV6ltuwbQqHuW3YaVnnxC1O4yx0ROKeS1oiZ79C5Cj+QH93YJk5cprBwDOHvifUFURvGYLbDFlvGedqGoAVBdOKlsmqwlIRlJmPtyEGpuJYNKJqGsnBt3/chJfbEuHUmboU7FF+IXbuEio1KVbryCprLy1ZSGlNnMb2qg6L3MlniNpLkQY96MFduWhFnG40O3QSMponrpphIciQAA3vKuLG48WbdlRd3JHHRafMBS3Ryar95ht2BtTa90gUdIcUo9ehaF2VXpPWrkFCtwbhOGGOMfchgy/SnOlyRDcyydNNmVcVnESGrRtoR88N0Atr23V+LYUnjSu4XJdPUWHDYOlEOKwPowlxvzeJN20O+b+SP48x5eLZVCEoUkZcXfLp1lyO";
        Long sdk = Finance.NewSdk();
        Long msg = Finance.NewSlice();
        Finance.DecryptData(sdk, result, v, msg);
        System.out.println(Finance.GetContentFromSlice(msg));
        Finance.FreeSlice(msg);
        Finance.DestroySdk(sdk);
    }

    static Map<String, SdkFileId> sdkFileIdMap = new HashMap<>();

    static {
        sdkFileIdMap.put("voice", chatMsg -> chatMsg.getVoice().getSdkFileId());
        sdkFileIdMap.put("meeting_voice_call", chatMsg -> chatMsg.getMeetingVoiceCall().getSdkFileId());
        sdkFileIdMap.put("image", chatMsg -> chatMsg.getImage().getSdkFileId());
    }

    interface SdkFileId {
        String getSdkFileId(ChatMsg chatMsg);
    }
}
