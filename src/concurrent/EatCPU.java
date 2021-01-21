package concurrent;

public class EatCPU {

    private static class Eater implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }

    private static class Printer implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) new Thread(new Eater()).start();
        new Thread(new Printer()).start();
    }
}
