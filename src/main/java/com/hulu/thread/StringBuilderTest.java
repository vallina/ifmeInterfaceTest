package com.hulu.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StringBuilderTest {
    public static void main(String[] args) {
        final StringBuilder sb = new StringBuilder("#####-----");
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        for (int i=0; i<1000000; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    sb.reverse();
                    System.out.println(sb);
                }
            });
        }
        threadPool.shutdown();
    }
}
