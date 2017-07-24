package com.hulu.utils;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yumi on 2017/3/4.
 */

public class PropertiesUtil {

    public static String getValue(String key,String configName) {

            // 加载键值对数据
            Properties prop = new Properties();

            String configPath = PropertiesUtil.class.getClassLoader().getResource(configName).getPath();

            System.out.println(configPath);

            String value = null;
            try {
                FileReader fr = new FileReader(configPath);
                prop.load(fr);
                fr.close();
                // 获取数据
                value = prop.getProperty(key);
            }catch (Exception e){
                e.printStackTrace();
            }


        return value;
    }

}
