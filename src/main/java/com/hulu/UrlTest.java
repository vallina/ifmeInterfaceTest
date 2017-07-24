package com.hulu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yumi on 2017/7/22.
 */
public class UrlTest {
    public static void main(String[] args) {
        try {
            String encode = URLEncoder.encode("WY6Q4gG0Ks/f413+ZkcCbopfPHvQr53fO53ETwKdRDywYsCUJ7mNY3T0T7j3FVD4");

            System.out.print(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
