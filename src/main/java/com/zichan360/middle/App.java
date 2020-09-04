package com.zichan360.middle;

import com.tencent.wework.Finance;
import com.zichan360.middle.util.RsaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haoxp
 * @date 20/8/31
 */
@RestController
@SpringBootApplication
@RequestMapping("/chatMsg/audit")
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Value("${encrypt.rsa}")
    private String rsa;


    @RequestMapping("/status")
    private String status() {
        return "running";
    }

    @Override
    public void run(String... args) throws Exception {
        String encryptKey = "JL+Se6tGLMZ6+jKriZgi2bW3v1Zr92LL4jc8EEhFiE2njztSMbjEO8XgThzCTxCaT0E7qmaJp4HXadsOlWU09DnRSdAlfxpoKMV/g5K5LJh/GsTqV8aTx7Q9ZBipsKk04iVpnnOyGt59X465xqFNf1amwIgxb2TAt573ll0pK22hngQA5wQTGTJfTXPL69cuKQuSjMjzkPoohgHCA6hIZ9vkhZKFihJTnPUwDb6EXnZn6qYPnYR+ZgkvVCwzcACXZm/dblJamaTsvHD1r8+Knf5Z94uHOMPFxzsLo9Orf0xlEWcd0XEYuZ71ck5CWmpvEeWV9dluA7DukcBhN0c2mw==";
        String result = "8HtkwcOJwG8c0TmJWpAQMLuF5YC7mVlpitpjQYTB2hIzs+wam9IsGSfQvWbunshO6tFzgbT4cIQIhwOInhhblQ==";
        String v = "9SuVDukT+Ld1XYGyvjW2kE8MjwdszecdWDYCFGVoPipwfRiIexTnakr4QEbcvAZJ2Y9Nx7zQbVR3PUCoxKEQNDlAibDWaB/myaD37VwgwkkfeB42M7YwKQWRi4MTvCGOSPu2Rk1Z7WlgucK6yfEMjIjKDM7ol5A7USSKyG5RGOPy5ErI4FZaZm1rM5n1FAx8R6cFiRxnv7TAji/zgWc9BMS1C8vJiysuyMuIJ9Ob0Rf0Wj4/toANkB+NrTjUdqQQUvM8IzGioPrPropCrKpHoPTW+FoCTbGHKoCo7Y1wlB7HbpFrSLOWjdKJvZXnvF2bI4Mcn9PyyitqRVQQaODhwos65lJItRGs+peqgYlvSbcWzsW/M+8vtv+A90MdiBKxArkKvyh5YyupPb0bxD1L4/WjSbTtFkciyA6Vlkf+Qnaq793H1NBwN1N0EFkRPZa/LthGx5Onkq56gvbTGEhgrlAzGUgc79RV9RUSKwXwQsFMH3AsO5YYN80q1nN6ejYfjx6fC4AJHLV39IUNhopNKW/UiOT607rASbzLkYOcAGVAr8x43zvXdI0Po3NwZ/ZBbVxgN+1gSCvQoPrbi/pe0MOk03GDgE1qHovg0xCS5wmuc89G+skRTy7huBREFQHWc2UV6ltuwbQqHuW3YaVnnxC1O4yx0ROKeS1oiZ79C5Cj+QH93YJk5cprBwDOHvifUFURvGYLbDFlvGedqGoAVBdOKlsmqwlIRlJmPtyEGpuJYNKJqGsnBt3/chJfbEuHUmboU7FF+IXbuEio1KVbryCprLy1ZSGlNnMb2qg6L3MlniNpLkQY96MFduWhFnG40O3QSMponrpphIciQAA3vKuLG48WbdlRd3JHHRafMBS3Ryar95ht2BtTa90gUdIcUo9ehaF2VXpPWrkFCtwbhOGGOMfchgy/SnOlyRDcyydNNmVcVnESGrRtoR88N0Atr23V+LYUnjSu4XJdPUWHDYOlEOKwPowlxvzeJN20O+b+SP48x5eLZVCEoUkZcXfLp1lyO";
        Long sdk = Finance.NewSdk();
        Long msg = Finance.NewSlice();
        Finance.DecryptData(sdk, RsaUtil.dencrypt(encryptKey, rsa), v, msg);
        System.out.println(Finance.GetContentFromSlice(msg));
        Finance.FreeSlice(msg);
        Finance.DestroySdk(sdk);
    }
}
