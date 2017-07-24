package com.hulu.common;

import com.alibaba.fastjson.JSONObject;
import com.hulu.encrypt.MD5Utils;
import com.hulu.http.HttpRequestUtils;
import com.hulu.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

//import org.junit.Before;
//import org.junit.Test;

/**
 * 业务接口测试
 * @author likaige
 * @date 2017-03-04.
 */
public class BaseTest {
    private static Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

    // 加密的盐
    public static final String DES_SALT = "XgYsd_JVBs+!@#$%Nfd+_-1jjb!//^&*()";

    //开发环境业务域名
    public static final String BASE_URL = PropertiesUtil.getValue("url","config.properties");

    //用户登录的token
    public static String token;
    public static String key;
    public static String iv;

    @BeforeTest
    public void init() {
        log.info("init");
        this.sms_codev1();
        this.loginv1();
    }

    @Test
    public void sms_codev1() {
        log.info("sms_codev1...");
        String url = BASE_URL + "login/sms_codev1";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone_number", "18911123299");
        jsonObject.put("device_id", "1234");

        JSONObject jsonObject1 = HttpRequestUtils.httpPost(url, jsonObject, null, null);

        log.info("fdsafdsafsdf={}", jsonObject1);

    }


    @Test
    public void loginv1() {
        log.info("loginv1...");

        String url = BASE_URL + "login/loginv1";

        String code = new SimpleDateFormat("MMdd").format(new Date());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone_number", "18911123299");
        jsonObject.put("code", code);

        JSONObject resultJsonObject = HttpRequestUtils.httpPost(url, jsonObject, null, null);
        log.info("resultJsonObject=={}", resultJsonObject);

        JSONObject data = resultJsonObject.getJSONObject("data");

        token = data.getString("token");
        String uid = data.getString("uid");
        log.info("token=={}", token);
        log.info("uid=={}", uid);

        String md5str = MD5Utils.md5(uid + DES_SALT + token);
        key = md5str.substring(0,24);
        iv = md5str.substring(24,32);

        log.info("key={} iv={}", key, iv);
    }

}
