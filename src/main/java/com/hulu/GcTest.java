package com.hulu;

import java.util.ArrayList;
import java.util.List;

public class GcTest {

    private static final String base = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(10000);

        //List<String> list = new ArrayList<>();
        List<MyObj> ObjList = new ArrayList<>();
        int i = 0;
        //String str = "";
        while (true) {
            i++;
            //str = base + System.currentTimeMillis() + "";
            //list.add(str);

            ObjList.add(new MyObj());

            Thread.sleep(1000);
            System.out.println(i);
        }

    }

}

