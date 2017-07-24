package com.hulu.encrypt;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    private static final String[] strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5Utils() {
    }

    private static String byteToArrayString(byte var0) {
        int var1 = var0;
        if(var0 < 0) {
            var1 = var0 + 256;
        }

        int var2 = var1 / 16;
        int var3 = var1 % 16;
        return strDigits[var2] + strDigits[var3];
    }

    private static String byteToNum(byte var0) {
        int var1 = var0;
        System.out.println("iRet1=" + var0);
        if(var0 < 0) {
            var1 = var0 + 256;
        }

        return String.valueOf(var1);
    }

    private static String byteToString(byte[] var0) {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1.append(byteToArrayString(var0[var2]));
        }

        return var1.toString();
    }

    public static String md5(String var0) {
        String var1 = null;

        try {
            new String(var0);
            MessageDigest var2 = MessageDigest.getInstance("MD5");
            var1 = byteToString(var2.digest(var0.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return var1;
    }

    public static void main(String[] var0) {
        System.out.println(md5("000000"));
    }
}

