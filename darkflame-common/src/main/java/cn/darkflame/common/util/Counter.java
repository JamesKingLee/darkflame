package cn.darkflame.common.util;

@SuppressWarnings("unused")
public class Counter {

    public volatile int count;

    public Counter(int count) {
        this.count = count;
    }

    public synchronized void countDown() {
        this.count--;
    }

    public synchronized void countUp() {
        this.count++;
    }

    public int getCount() {
        return count;
    }

    public synchronized boolean isZero() {
        return count == 0;
    }

}
