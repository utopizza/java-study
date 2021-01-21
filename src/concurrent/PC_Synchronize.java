package concurrent;

public class PC_Synchronize {
    private static Object[] buffer = new Object[3];
    private static int size = 0;

    private static class Producer implements Runnable {
        @Override
        public void run() {
            synchronized (buffer) {
                while (size == buffer.length - 1) {
                    try {
                        buffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[++size] = new Object();
                System.out.println(Thread.currentThread().getName() + ", size=" + size);
                buffer.notify(); // notify() will cause dead lock
            }
        }

    }

    private static class Consumer implements Runnable {
        @Override
        public void run() {
            synchronized (buffer) {
                while (size == 0) {
                    try {
                        buffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[size--] = null;
                System.out.println(Thread.currentThread().getName() + ", size=" + size);
                buffer.notify(); // notify() will cause dead lock
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) new Thread(new Consumer(), "Consumer_" + i).start();
        for (int i = 0; i < 100; i++) new Thread(new Producer(), "Producer_" + i).start();
    }
}
