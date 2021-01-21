package concurrent;

import java.util.concurrent.*;

public class MyExecutor {
    private static final Object lock = new Object();
    public static Executor executor = Executors.newFixedThreadPool(10);
    public static ExecutorService executorService = new ThreadPoolExecutor(
            5,
            10,
            10L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new MyThreadFactory(),
            new MyRejectExecutionHandler());

    public static class MyJob implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            synchronized (lock) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class MyThreadFactory implements ThreadFactory {
        private volatile int threadId = 0;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "thread-" + String.valueOf(threadId++));
        }
    }

    public static class MyRejectExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("reject a job");
        }
    }

    public static void main(String[] args) {

        ArrayBlockingQueue<MyJob> arrayBlockingQueue;
        LinkedBlockingQueue<MyJob> linkedBlockingQueue;
        PriorityBlockingQueue<MyJob> priorityBlockingQueue;
        SynchronousQueue<MyJob> synchronousQueue;
        DelayQueue<Delayed> delayQueue;

        for (int i = 0; i < 21; i++) {
            executorService.execute(new MyJob());
        }
    }
}
