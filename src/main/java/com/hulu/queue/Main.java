package com.hulu.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) throws InterruptedException {
        log.info("test....");


        DelayQueue<DelayedElement> delayQueue = new DelayQueue<>();

        DelayedElement el1 = new DelayedElement(5000);

        DelayedElement el2 = new DelayedElement(3000);

        delayQueue.offer(el1);
        delayQueue.offer(el2);

        Delayed poll = null;

        while (poll == null) {

            poll = delayQueue.poll();
            log.info("poll result={}", poll);
            DelayedElement peek = delayQueue.peek();
            log.info("peek result={}", peek);

            Thread.sleep(500);

            System.out.println("-----------------------------------------------------------");
        }

    }

}
