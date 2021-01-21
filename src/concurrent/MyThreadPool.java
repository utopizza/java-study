package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MyThreadPool<Job extends Runnable> {
    private static final int Max_Worker_Numbers = 10;
    private static final int Default_Worker_Numbers = 5;
    private static final int Min_Worker_Numbers = 1;
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    private int workerNum = Default_Worker_Numbers;
    private AtomicLong threadNum = new AtomicLong();

    private class Worker implements Runnable {
        private volatile boolean running = true;

        public void run() {
            while (running) {
                Job job = null;

                // wait for a job
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst(); // get a job from the jobs
                }

                // do the job
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }

    public MyThreadPool() {
        initializeWorkers(Default_Worker_Numbers);
    }

    public MyThreadPool(int num) {
        workerNum = num > Max_Worker_Numbers ? Max_Worker_Numbers : num < Min_Worker_Numbers ? Min_Worker_Numbers : num;
        initializeWorkers(workerNum);
    }

    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread workerThread = new Thread(worker, "Thread_Worker_" + threadNum.incrementAndGet());
            workerThread.start();
        }
    }

    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workerNum > Max_Worker_Numbers) num = Max_Worker_Numbers;
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify(); // notify workers
            }
        }
    }

    public int getJobSize() {
        return jobs.size();
    }

    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    public void removeWorkers(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) throw new IllegalArgumentException("beyond workNum");
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }
}
