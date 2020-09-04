package com.zichan360.middle.util;

import com.zichan360.middle.service.MsgService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author haoxp
 * @date 20/8/31
 */
public class RsaUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtil.class);
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小（如果秘钥是1024bit,解密最大块是128,如果秘钥是2048bit,解密最大块是256）
     */
    private static final int MAX_DECRYPT_BLOCK = 256;
    private static PrivateKey globalPrivateKey = null;
    private static PublicKey globalPublicKey = null;

    public static PrivateKey getPrivateKey(String pk) throws Exception {
        if (globalPrivateKey != null) {
            return globalPrivateKey;
        }

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(pk));
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            globalPrivateKey = privateKey;
            return globalPrivateKey;
        } catch (Exception e) {
            LOGGER.error("获取私钥异常", e);
            throw e;
        }
    }

    public static PublicKey getPublicByPublic(String keypath) throws Exception {
        if (globalPublicKey != null) {
            return globalPublicKey;
        }
        InputStream in = RSAUtil.class.getClassLoader().getResourceAsStream(keypath);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String readLine = null;

        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) != 45) {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        in.close();
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509EncodedKeySpec ks = new X509EncodedKeySpec(Base64.decodeBase64(sb.toString()));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        globalPublicKey = kf.generatePublic(ks);
        return globalPublicKey;
    }

    public static String encrypt(String data, String publicKeyPath) throws Exception {
        PublicKey pubKey = getPublicByPublic(publicKeyPath);
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] dataByte = data.getBytes("UTF-8");
        int inputLen = dataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataByte, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();

        String outStr = Base64.encodeBase64String(encryptedData);
        return outStr;
    }

    public static String dencrypt(String str, String privateKeyStr) {
        //64位解码加密后的字符串
        try {
            byte[] encryptedData = Base64.decodeBase64(str.getBytes("UTF-8"));
            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            String outStr = new String(decryptedData, "UTF-8");
            return outStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        String encryptKey = "JL+Se6tGLMZ6+jKriZgi2bW3v1Zr92LL4jc8EEhFiE2njztSMbjEO8XgThzCTxCaT0E7qmaJp4HXadsOlWU09DnRSdAlfxpoKMV/g5K5LJh/GsTqV8aTx7Q9ZBipsKk04iVpnnOyGt59X465xqFNf1amwIgxb2TAt573ll0pK22hngQA5wQTGTJfTXPL69cuKQuSjMjzkPoohgHCA6hIZ9vkhZKFihJTnPUwDb6EXnZn6qYPnYR+ZgkvVCwzcACXZm/dblJamaTsvHD1r8+Knf5Z94uHOMPFxzsLo9Orf0xlEWcd0XEYuZ71ck5CWmpvEeWV9dluA7DukcBhN0c2mw==";
        String result = "8HtkwcOJwG8c0TmJWpAQMLuF5YC7mVlpitpjQYTB2hIzs+wam9IsGSfQvWbunshO6tFzgbT4cIQIhwOInhhblQ==";

        System.out.println(dencrypt(encryptKey, MsgService.rsa));
        System.out.println(result);
    }

}
