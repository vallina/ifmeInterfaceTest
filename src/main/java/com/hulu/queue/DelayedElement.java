package com.hulu.queue;

import java.util.Calendar;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedElement implements Delayed {

    private final long delay;
    private final long expire;

    public DelayedElement(long delay) {
        this.delay = delay;
        expire = Calendar.getInstance().getTimeInMillis() + delay;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return expire - Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.delay - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "DelayedElement is " + delay + " elapsed time is " + getDelay(TimeUnit.DAYS);
    }
}
