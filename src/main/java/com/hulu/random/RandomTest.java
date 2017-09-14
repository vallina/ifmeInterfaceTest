package com.hulu.random;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {

    //private Random random = new Random();
    //private ThreadLocalRandom random = ThreadLocalRandom.current();

    public static void main(String[] args) throws InterruptedException {
        new RandomTest().start();

    }

    private void start() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        int count = 1000 * 10000;
        final CountDownLatch latch = new CountDownLatch(count);

        long t = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {

            final int finalI = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //Random random = new Random();
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    int nextInt = random.nextInt(100);
                    //System.out.println(finalI);
                    //System.out.println(Thread.currentThread().getName());
                    //System.out.println(random);

                    latch.countDown();
                }
            });
        }
        latch.await();

        threadPool.shutdown();

        long t2 = System.currentTimeMillis();
        System.out.println("time=" + (t2 - t));
    }
}
