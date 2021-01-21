package concurrent;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MyThread {
    public static void main(String[] args) {

    }
}

class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TimeUtils {
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static void printLog(String info) {
        System.out.println(info + " " + getCurrentTime());
    }
}


class ThreadPriority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    static class Job implements Runnable {
        private int priority;
        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }

    public static void main(String[] args) {
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notEnd = false;
        for (Job job : jobs) {
            System.out.println("Job Priority:" + job.priority + ",Count:" + job.jobCount);
        }
    }
}

class ThreadState {
    static class TimeWaiting implements Runnable {
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    static class Waiting implements Runnable {
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }
}

class ThreadDaemon {
    static class DaemonRunner implements Runnable {
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }
}

class ThreadInterrupted {
    static class SleepRunner implements Runnable {
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BusyRunner implements Runnable {
        public void run() {
            while (true) {

            }
        }
    }

    public static void main(String[] args) {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        SleepUtils.second(5);

        // set interrupt target
        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());

        SleepUtils.second(2);
    }
}

class ThreadShutdown {
    private static class Runner implements Runnable {
        private long i;
        private volatile boolean on = true;

        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            System.out.println("count i=" + i);
        }

        public void cancel() {
            on = false;
        }
    }

    public static void main(String[] args) {

        // shutdown by interrupt()
        Runner one = new Runner();
        Thread countThread = new Thread(one, "runner-1");
        countThread.start();

        SleepUtils.second(2);
        countThread.interrupt();

        // shutdown by some flags
        Runner two = new Runner();
        countThread = new Thread(two, "runner-2");
        countThread.start();

        SleepUtils.second(2);
        two.cancel();
    }
}

class ThreadSynchronized {
    public void fun() {
        synchronized (this) {

        }
    }

    public synchronized void fun2() {

    }
}

class ThreadWaitNotify {
    private static boolean flag = true;
    private static Object lock = new Object();

    private static class WaitRunner implements Runnable {
        public void run() {
            synchronized (lock) {
                // wait for flag
                while (flag) {
                    try {
                        TimeUtils.printLog(Thread.currentThread() + " flag is true. waiting at");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // flag is false
                TimeUtils.printLog(Thread.currentThread() + " flag is false. running at");
            }
        }
    }

    private static class NotifyRunner implements Runnable {
        public void run() {
            synchronized (lock) {
                TimeUtils.printLog(Thread.currentThread() + " hold lock. notifying at");
                lock.notify();
                flag = false;
                SleepUtils.second(5);
            }
            synchronized (lock) {
                TimeUtils.printLog(Thread.currentThread() + " hold lock again. sleep at");
                SleepUtils.second(5);
            }
        }
    }

    public static void main(String[] args) {
        Thread waitThread = new Thread(new WaitRunner(), "WaitThread");
        Thread notifyThread = new Thread(new NotifyRunner(), "NotifyThread");
        waitThread.start();
        SleepUtils.second(2);
        notifyThread.start();
    }
}

class ThreadPipe {
    private static class PrintRunner implements Runnable {
        private PipedReader in;

        public PrintRunner(PipedReader in) {
            this.in = in;
        }

        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        try {
            out.connect(in);
            Thread printThread = new Thread(new PrintRunner(in), "PrintThread");
            printThread.start();
            int receive = 0;

            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadJoin {
    private static class Domino implements Runnable {
        private Thread threadToJoin;

        public Domino(Thread threadToJoin) {
            this.threadToJoin = threadToJoin;
        }

        public void run() {
            try {
                threadToJoin.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate.");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread current = new Thread(new Domino(previous), String.valueOf(i));
            current.start();
            previous = current;
        }
    }
}

class ThreadLocalDemo {
    private static final ThreadLocal<Long> Time_ThreadLocal = new ThreadLocal<Long>();

    public static final void begin() {
        Time_ThreadLocal.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - Time_ThreadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalDemo.begin();
        SleepUtils.second(1);
        System.out.println("Cost: " + ThreadLocalDemo.end() + " mills");
    }
}

class ThreadByCallable {

    private static class MyCallable implements Callable<Double> {
        @Override
        public Double call() {
            return 12.1;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Double> futureTask = new FutureTask<>(new MyCallable());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}