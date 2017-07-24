package com.hulu.friend;

import com.alibaba.fastjson.JSONObject;
import com.hulu.common.BaseTest;
import com.hulu.excel.ExcelParam;
import com.hulu.excel.ExcelUtils;
import com.hulu.http.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.net.URLEncoder.encode;

/**
 * Created by yumi on 2017/4/19.
 */
public class DelfriendTest extends BaseTest{
    private static Logger log = LoggerFactory.getLogger(HttpRequestUtils.class);

    @DataProvider(name="testData")
    public static Object[][] data() throws IOException
    {
        String excelPath = "D:/friend.xls";
        String sn = "delfriend";
        int start = 7; //取參數文件的索引编号为7的列為起始列
        int paraNum = 4; // 取连续三列为参数
        File excel = new File(excelPath);
        Object[][] testData = ExcelUtils.getTestData(excel, sn, start, paraNum);
        log.info("testDatas======{}", testData);
        return testData;
    }


    @Test(dataProvider="testData")
    public void delfriend(String fuid,String httpcodeExp, String errnoExp,String errmsgExp,int row) throws UnsupportedEncodingException {
        log.info("delfriend... token={} key={} iv={} row={}", token, key, iv, row);

        //String url = BASE_URL + "personal/personalpagev1?token=" + token;
        String url = BASE_URL + "friend/delfriend?token=" + encode(token, "UTF-8");

        System.out.println("url:  "+url);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fuid", fuid);

        System.out.println("fuid="+fuid);

        JSONObject resultJsonObject = HttpRequestUtils.httpPost(url, jsonObject, key, iv);
        log.info("resultJsonObject=={}", resultJsonObject);

        if(resultJsonObject == null || resultJsonObject.isEmpty()){
            log.error("resultJsonObject is null!!!!!");
            return;
        }

       // int num = ExcelUtils.readRows("D:/2222.xls", "personalpage");
        // System.out.println("num is: " + num);

        List<ExcelParam> paramList = new ArrayList<>();

        //ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 8, resultJsonObject.toString());
        ExcelParam p1 = new ExcelParam(row, 14, resultJsonObject.toString());// 服务器实际返回结果写入excel指定的行和列
        paramList.add(p1);

        String errnoAct = resultJsonObject.getString("errno");
        ExcelParam pErrnoAct = new ExcelParam(row, 12, errnoAct);
        String errmsgAct = resultJsonObject.getString("errmsg");
        ExcelParam pErrmsgAct = new ExcelParam(row, 13, errmsgAct);

        // ExcelUtils.writeExcel("D:/2222.xls", "personalpage", 2, 8, errnoAct);
        log.info("errnoAct: " + errnoAct);
        Assert.assertEquals(errnoAct, errnoExp);  // 比对errno的预期结果和实际结果
        Assert.assertEquals(errmsgAct, errmsgExp); // 比对errsmg的预期结果和实际结果
        if (errnoExp.equals(errnoAct) && errmsgExp.equals(errmsgAct)) {
            //ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 9, "pass");
            ExcelParam pRes = new ExcelParam(row, 15, "passed");
            paramList.add(pRes);
        } else {
            //ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 9, "failed");

            ExcelParam pRes = new ExcelParam(row, 15, "failed");
            paramList.add(pRes);
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        // ExcelUtils.writeExcel("D:/2222.xls", "personalpage", row, 10, df.format(new Date()));
        ExcelParam pDate = new ExcelParam(row, 16, df.format(new Date()));
        paramList.add(pDate);

        ExcelUtils.writeExcel("D:/friend.xls", "personalpage", paramList);


        System.out.println("----------------------------------------");

    }



}
