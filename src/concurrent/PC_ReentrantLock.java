package concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PC_ReentrantLock {
    private static Object[] buffer = new Object[3];
    private static int size = 0;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition NotEmpty = lock.newCondition();
    private static Condition NotFull = lock.newCondition();

    private static class Producer implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                while (size == buffer.length - 1) {
                    try {
                        NotFull.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[++size] = new Object();
                System.out.println(Thread.currentThread().getName() + ", size=" + size);
                NotEmpty.signal(); // signal() will cause dead lock
            } finally {
                lock.unlock();
            }
        }
    }

    private static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                while (size == 0) {
                    try {
                        NotEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[size--] = null;
                System.out.println(Thread.currentThread().getName() + ", size=" + size);
                NotFull.signal(); // signal() will cause dead lock
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) new Thread(new Consumer(), "Consumer_" + i).start();
        for (int i = 0; i < 500; i++) new Thread(new Producer(), "Producer_" + i).start();
    }
}
