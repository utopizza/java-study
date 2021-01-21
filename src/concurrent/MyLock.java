package concurrent;

import java.util.concurrent.locks.*;

public class MyLock {
    public static void main(String[] args) {

    }
}

class ReentrantLockTest {
    private static Lock nonFairLock = new ReentrantLock();
    private static Lock fairLock = new ReentrantLock(true);

    private static class nonFairRunner implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " acquiring lock...");
            nonFairLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " acquiring lock successfully. Begin to work.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " finish working. Release lock.");
                nonFairLock.unlock();
            }
        }
    }

    private static class fairRunner implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " acquiring lock...");
            fairLock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " acquiring lock successfully. Begin to work.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " finish working. Release lock.");
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new nonFairRunner(), String.valueOf(i)).start();
            //new Thread(new fairRunner(), String.valueOf(i)).start();
        }
    }
}

class ReentrantReadWriteLockTest {
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static class writerRunner implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " acquiring write lock...");
            readWriteLock.writeLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " acquire write lock successfully. Begin writing...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " finish writing. Release write lock.");
                readWriteLock.writeLock().unlock();
            }
        }
    }

    private static class readerRunner implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " acquiring read lock...");
            readWriteLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + " acquire read lock successfully. Begin reading...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + " finish reading. Release read lock.");
                readWriteLock.readLock().unlock();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new readerRunner(), "reader " + i).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(new writerRunner(), "writer " + i).start();
        }
    }
}

class ConditionTest {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final Condition notFull = reentrantLock.newCondition();
    private static final Condition notEmpty = reentrantLock.newCondition();
    private static volatile Object[] buffer = new Object[5];
    private static volatile int size = 0;

    private static class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    // produce an object
                    Thread.sleep(2000);

                    // wait for empty place to put object
                    reentrantLock.lock();
                    while (size == buffer.length - 1) {
                        System.out.println(Thread.currentThread().getName() + " waiting for empty place...");
                        notFull.await();
                    }

                    // put the object into the buffer
                    buffer[size++] = new Object();
                    System.out.println(Thread.currentThread().getName() + " added an object to buffer. Buffer size is " + size);

                    // notify consumers if the buffer is not empty
                    if (size > 0) {
                        notEmpty.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }
    }

    private static class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    // wait until buffer is not empty
                    reentrantLock.lock();
                    while (size == 0) {
                        System.out.println(Thread.currentThread().getName() + " waiting for objects...");
                        notEmpty.await();
                    }

                    // remove an object from buffer
                    buffer[--size] = null;
                    System.out.println(Thread.currentThread().getName() + " removed an object from buffer. Buffer size is " + size);

                    // notify the producers if the buffer is not full
                    if (size < buffer.length - 1) {
                        notFull.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }

                // consume the object
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new Producer(), "Producer_" + i).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(new Consumer(), "Consumer_" + i).start();
        }
    }
}