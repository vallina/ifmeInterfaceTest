package com.hulu.fc;

import com.alibaba.fastjson.JSONObject;
import com.hulu.common.BaseTest;
import com.hulu.excel.ExcelParam;
import com.hulu.excel.ExcelUtils;
import com.hulu.encrypt.MD5Utils;
import com.hulu.http.HttpRequestUtils;
//import org.junit.Before;
//import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.net.URLEncoder.encode;

/**
 * 业务接口测试
 * @author likaige
 * @date 2017-03-04.
 */
public class FCTest extends BaseTest {
    private static Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

    @DataProvider(name="testData")
    public static Object[][] data() throws IOException
    {
        String excelPath = "D:/2222.xls";
        String sn = "personalpage";
        int start = 5;
        int paraNum = 3;
        File excel = new File(excelPath);
        Object[][] testData = ExcelUtils.getTestData(excel, sn, start, paraNum);
        log.info("testDatas======{}", testData);
        return testData;
    }


    @Test(dataProvider="testData")
    public void personalpagev1(String puid,String lmsgid, String errnoExp,int row) throws UnsupportedEncodingException {
        log.info("personalpagev1... token={} key={} iv={} row={}", token, key, iv, row);

        //String url = BASE_URL + "personal/personalpagev1?token=" + token;
        String url = BASE_URL + "personal/personalpagev1?token=" + encode(token, "UTF-8");

        System.out.println("url:  "+url);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageuid", puid);
        jsonObject.put("lastmsgid", lmsgid);

        System.out.println("puid="+puid);
        System.out.println("errnoExp=" +errnoExp);

        JSONObject resultJsonObject = HttpRequestUtils.httpPost(url, jsonObject, key, iv);
        log.info("resultJsonObject=={}", resultJsonObject);

        if(resultJsonObject == null || resultJsonObject.isEmpty()){
            log.error("resultJsonObject is null!!!!!");
            return;
        }

        int num = ExcelUtils.readRows("D:/2222.xls", "personalpage");
        System.out.println("num is: " + num);

        List<ExcelParam> paramList = new ArrayList<>();

        //ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 8, resultJsonObject.toString());
        ExcelParam p1 = new ExcelParam(row, 8, resultJsonObject.toString());
        paramList.add(p1);

        String errnoAct = resultJsonObject.getString("errno");
           // ExcelUtils.writeExcel("D:/2222.xls", "personalpage", 2, 8, errnoAct);
        log.info("errnoAct: " + errnoAct);
        Assert.assertEquals(errnoAct, errnoExp);
        if (errnoExp.equals(errnoAct)) {
            //ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 9, "pass");
            ExcelParam p2 = new ExcelParam(row, 9, "passed");
            paramList.add(p2);
        } else {
                //ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 9, "failed");

            ExcelParam p3 = new ExcelParam(row, 9, "failed");
            paramList.add(p3);
        }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
           // ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 10, df.format(new Date()));
        ExcelParam p4 = new ExcelParam(row, 10, df.format(new Date()));
        paramList.add(p4);

        ExcelUtils.writeExcel("D:/2222.xls", "personalpage", paramList);


        System.out.println("----------------------------------------");

    }



}
