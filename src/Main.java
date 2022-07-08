public class Main {
    public static void main(String[] args) {
        System.out.println("main thread id = " + Thread.currentThread().getId());
        Counter counter = new Counter();
        MyThread evenThread = new MyThread(counter, true);
        MyThread oddThead = new MyThread(counter, false);
        evenThread.start();
        oddThead.start();

        TestThread testThread = new TestThread();
        Thread thread = new Thread(testThread);
        thread.start();
    }
}

class SleepyThread extends Thread {
    @Override
    public void run() {
        System.out.println("thread id: " + Thread.currentThread().getId() + "sleep");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("thread id: " + Thread.currentThread().getId() + "wakeup");
    }
}

class MyThread extends Thread {
    private Counter counter;
    private boolean countEven;

    public MyThread(Counter counter, boolean countEven) {
        this.counter = counter;
        this.countEven = countEven;
    }

    @Override
    public void run() {
        counter.count(countEven);
    }
}

class TestThread implements Runnable {
    @Override
    public void run() {
    }
}

class Counter {
    public void count(boolean countEven) {
        synchronized (this) {
            for (int i=(countEven ? 0 : 1); i < 100; i+=2) {
                System.out.println("thread id: " + Thread.currentThread().getId() + ", i=" + i);
                notify();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notify();
        }
    }
}
