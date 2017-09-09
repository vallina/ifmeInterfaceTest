package com.hulu.thread;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArrayListTest {
    public static void main(String[] args) throws InterruptedException {
        final Vector<String> list = new Vector<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        int count = 100000;
        final CountDownLatch latch = new CountDownLatch(count);
        for (int i=0; i<count; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    latch.countDown();
                    list.add("1");
                }
            });
        }
        threadPool.shutdown();
        latch.await();
        System.out.println(list.size());
    }
}
