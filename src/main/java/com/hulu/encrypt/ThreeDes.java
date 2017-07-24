package com.hulu.encrypt;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class ThreeDes {
    public static final String secretKey = "qwert12345^&*()ZXCVB!@#$";
    public static final String iv = "P_+@*szp";
    private static final String encoding = "utf-8";

    public ThreeDes() {
    }

    public static String encode(String encodeText) {
        return encode(encodeText,secretKey,iv);
    }

    public static String encode(String var0, String var1, String var2) {
        SecretKey var3 = null;
        DESedeKeySpec var4 = null;

        try {
            var4 = new DESedeKeySpec(var1.getBytes());
        } catch (InvalidKeyException var19) {
            var19.printStackTrace();
        }

        SecretKeyFactory var5 = null;

        try {
            var5 = SecretKeyFactory.getInstance("desede");
        } catch (NoSuchAlgorithmException var18) {
            var18.printStackTrace();
        }

        try {
            var3 = var5.generateSecret(var4);
        } catch (InvalidKeySpecException var17) {
            var17.printStackTrace();
        }

        Cipher var6 = null;

        try {
            var6 = Cipher.getInstance("desede/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException var15) {
            var15.printStackTrace();
        } catch (NoSuchPaddingException var16) {
            var16.printStackTrace();
        }

        IvParameterSpec var7 = new IvParameterSpec(var2.getBytes());

        try {
            var6.init(1, var3, var7);
        } catch (InvalidKeyException var13) {
            var13.printStackTrace();
        } catch (InvalidAlgorithmParameterException var14) {
            var14.printStackTrace();
        }

        byte[] var8 = new byte[0];

        try {
            var8 = var6.doFinal(var0.getBytes("utf-8"));
        } catch (IllegalBlockSizeException var10) {
            var10.printStackTrace();
        } catch (BadPaddingException var11) {
            var11.printStackTrace();
        } catch (UnsupportedEncodingException var12) {
            var12.printStackTrace();
        }

        return Base64.encode(var8);
    }

    public static String decode(String decodeText) {
        return decode(decodeText,secretKey,iv);
    }

    public static String decode(String var0, String var1, String var2) {
        SecretKey var3 = null;
        DESedeKeySpec var4 = null;

        try {
            var4 = new DESedeKeySpec(var1.getBytes());
        } catch (InvalidKeyException var19) {
            var19.printStackTrace();
        }

        SecretKeyFactory var5 = null;

        try {
            var5 = SecretKeyFactory.getInstance("desede");
        } catch (NoSuchAlgorithmException var18) {
            var18.printStackTrace();
        }

        try {
            var3 = var5.generateSecret(var4);
        } catch (InvalidKeySpecException var17) {
            var17.printStackTrace();
        }

        Cipher var6 = null;

        try {
            var6 = Cipher.getInstance("desede/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException var15) {
            var15.printStackTrace();
        } catch (NoSuchPaddingException var16) {
            var16.printStackTrace();
        }

        IvParameterSpec var7 = new IvParameterSpec(var2.getBytes());

        try {
            var6.init(2, var3, var7);
        } catch (InvalidKeyException var13) {
            var13.printStackTrace();
        } catch (InvalidAlgorithmParameterException var14) {
            var14.printStackTrace();
        }

        byte[] var8 = new byte[0];

        try {
            var8 = var6.doFinal(Base64.decode(var0));
        } catch (IllegalBlockSizeException var11) {
            var11.printStackTrace();
        } catch (BadPaddingException var12) {
            var12.printStackTrace();
        }

        try {
            return new String(var8, "utf-8");
        } catch (UnsupportedEncodingException var10) {
            var10.printStackTrace();
            return "";
        }
    }

    public static String padding(String var0) {
        try {
            byte[] var1 = var0.getBytes("UTF8");
            int var2 = 8 - var1.length % 8;
            byte[] var3 = new byte[var1.length + var2];
            System.arraycopy(var1, 0, var3, 0, var1.length);

            for(int var4 = var1.length; var4 < var3.length; ++var4) {
                var3[var4] = 0;
            }

            return new String(var3, "UTF8");
        } catch (UnsupportedEncodingException var5) {
            System.out.println("Crypter.padding UnsupportedEncodingException");
            return null;
        }
    }

    public static class Base64 {
        private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

        public Base64() {
        }

        public static String encode(byte[] var0) {
            byte var1 = 0;
            int var2 = var0.length;
            StringBuffer var3 = new StringBuffer(var0.length * 3 / 2);
            int var4 = var2 - 3;
            int var5 = var1;
            int var6 = 0;

            int var7;
            while(var5 <= var4) {
                var7 = (var0[var5] & 255) << 16 | (var0[var5 + 1] & 255) << 8 | var0[var5 + 2] & 255;
                var3.append(legalChars[var7 >> 18 & 63]);
                var3.append(legalChars[var7 >> 12 & 63]);
                var3.append(legalChars[var7 >> 6 & 63]);
                var3.append(legalChars[var7 & 63]);
                var5 += 3;
                if(var6++ >= 14) {
                    var6 = 0;
                    var3.append(" ");
                }
            }

            if(var5 == var1 + var2 - 2) {
                var7 = (var0[var5] & 255) << 16 | (var0[var5 + 1] & 255) << 8;
                var3.append(legalChars[var7 >> 18 & 63]);
                var3.append(legalChars[var7 >> 12 & 63]);
                var3.append(legalChars[var7 >> 6 & 63]);
                var3.append("=");
            } else if(var5 == var1 + var2 - 1) {
                var7 = (var0[var5] & 255) << 16;
                var3.append(legalChars[var7 >> 18 & 63]);
                var3.append(legalChars[var7 >> 12 & 63]);
                var3.append("==");
            }

            return var3.toString();
        }

        private static int decode(char var0) {
            if(var0 >= 65 && var0 <= 90) {
                return var0 - 65;
            } else if(var0 >= 97 && var0 <= 122) {
                return var0 - 97 + 26;
            } else if(var0 >= 48 && var0 <= 57) {
                return var0 - 48 + 26 + 26;
            } else {
                switch(var0) {
                    case '+':
                        return 62;
                    case '/':
                        return 63;
                    case '=':
                        return 0;
                    default:
                        throw new RuntimeException("unexpected code: " + var0);
                }
            }
        }

        public static byte[] decode(String var0) {
            ByteArrayOutputStream var1 = new ByteArrayOutputStream();

            try {
                decode(var0, var1);
            } catch (IOException var5) {
                throw new RuntimeException();
            }

            byte[] var2 = var1.toByteArray();

            try {
                var1.close();
                var1 = null;
            } catch (IOException var4) {
                System.err.println("Error while decoding BASE64: " + var4.toString());
            }

            return var2;
        }

        private static void decode(String var0, OutputStream var1) throws IOException {
            int var2 = 0;
            int var3 = var0.length();

            while(true) {
                while(var2 < var3 && var0.charAt(var2) <= 32) {
                    ++var2;
                }

                if(var2 == var3) {
                    break;
                }

                int var4 = (decode(var0.charAt(var2)) << 18) + (decode(var0.charAt(var2 + 1)) << 12) + (decode(var0.charAt(var2 + 2)) << 6) + decode(var0.charAt(var2 + 3));
                var1.write(var4 >> 16 & 255);
                if(var0.charAt(var2 + 2) == 61) {
                    break;
                }

                var1.write(var4 >> 8 & 255);
                if(var0.charAt(var2 + 3) == 61) {
                    break;
                }

                var1.write(var4 & 255);
                var2 += 4;
            }

        }
    }
}

