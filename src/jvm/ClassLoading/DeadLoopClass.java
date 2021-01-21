package jvm.ClassLoading;

public class DeadLoopClass {
    static {
        // while(true); -- will not compile
        if (true) {
            System.out.println(Thread.currentThread()+" init DeadLoopClass");
            while (true) ;
        }
    }

    public static void main(String[] args) {
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + " end");
            }
        };

        Thread thread1 = new Thread(runner);
        Thread thread2 = new Thread(runner);
        thread1.start();
        thread2.start();
    }
}
