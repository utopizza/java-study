package jvm.JMM;

public class VolatileTest2 {

    // 如果不用 volatile 修饰，则可能发生指令重排，
    // 导致 ready=true 先于 number=1 执行而线程打印 number=0
    static volatile boolean ready = false;
    static int number;

    static class Runner implements Runnable {
        @Override
        public void run() {
            while (!ready) Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new Thread(new Runner()).start();
        number = 1;
        ready = true;
    }
}

class Singleton {

    // 如果此处不使用 volatile 修饰，则导致 instance=new Singleton() 发生指令重排，
    // 返回一个没有初始化完整的 instance 对象
    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton.getInstance();
    }
}