package concurrent;

import java.util.concurrent.*;

public class ConcurrentTools {
    public static void main(String[] args) {

    }
}

class CountDownLatchTest {
    private static final int countDownNum = 10;
    private static CountDownLatch countDownLatch = new CountDownLatch(countDownNum);

    private static class Runner implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                System.out.println("Thread " + Thread.currentThread().getName() + " finished.");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= countDownNum; i++) {
            new Thread(new Runner(), String.valueOf(i)).start();
        }
        try {
            System.out.println("Main thread begin to wait on countDownLatch.");
            countDownLatch.await();
            System.out.println("Main thread continue to run.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class CyclicBarrierTest {
    private static final int barrierNum = 10;
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(barrierNum);

    private static class Runner implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " begin to work.");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " finish working, waiting for other threads.");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " finish waiting, going next.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= barrierNum; i++) {
            new Thread(new Runner(), String.valueOf(i)).start();
        }
    }
}

class SemaphoreTest {
    private static final int semaphoreNum = 3;
    private static Semaphore semaphore = new Semaphore(semaphoreNum);

    private static class Runner implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " acquiring semaphore.");
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " acquire semaphore successfully. Begin to work...");
                Thread.sleep(1000);
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " finished working and released semaphore.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runner(), String.valueOf(i)).start();
        }
    }
}

class ExchangerTest {
    private static Exchanger<String> exchanger = new Exchanger<String>();

    private static class Runner implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting on exchanging...");
                String name = exchanger.exchange(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " finished exchanging. The other thread's name is " + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new Runner(), "ThreadA").start();
        new Thread(new Runner(), "ThreadB").start();
        new Thread(new Runner(), "ThreadC").start();
        new Thread(new Runner(), "ThreadD").start();
    }
}