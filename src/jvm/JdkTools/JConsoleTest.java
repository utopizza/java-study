package jvm.JdkTools;

import java.io.IOException;

public class JConsoleTest {
    public static void main(String[] args) throws IOException {

        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //br.readLine();
        //createBusyThread();
        //br.readLine();
        //Object object = new Object();
        //createLockThread(object);

        // dead lock test
        for (int i = 0; i < 100; i++) {
            new Thread(new SynAddRunner(1, 2)).start();
            new Thread(new SynAddRunner(2, 1)).start();
        }
    }

    private static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) ;
            }
        }, "testBusyThread");
        thread.start();
    }

    private static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        thread.start();
    }

    private static class SynAddRunner implements Runnable {
        int a, b;

        public SynAddRunner(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.println(a + b);
                }
            }
        }
    }
}

