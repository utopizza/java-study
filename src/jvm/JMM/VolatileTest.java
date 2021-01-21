package jvm.JMM;

public class VolatileTest {

    // volatile 无法保证“读-计算-写”的原子性
    // 可能存在多个线程读取同一个 count 值，同时加1，再写回主存的count，导致写丢失(只有读缓存时才判断是否失效)
    public static volatile int count = 0;

    public static void increase() {
        count++;
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        // 每次执行结果都可能不一样
        while (Thread.activeCount() > 2) Thread.yield();
        System.out.println(count);
    }
}
