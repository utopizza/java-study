package designPatterns;

public class SingletonPattern {
    public static void main(String[] args) {

    }
}

class SingleObject1 {
    private static SingleObject1 instance = new SingleObject1();

    private SingleObject1() {
    }

    private static SingleObject1 getInstance() {
        return instance;
    }
}

class SingleObject2 {
    private volatile static SingleObject2 instance;

    private SingleObject2() {
    }

    private static synchronized SingleObject2 getInstance() {
        if (instance == null) {
            instance = new SingleObject2();
        }
        return instance;
    }
}

class SingleObject3 {
    private volatile static SingleObject3 instance;

    private SingleObject3() {
    }

    private static SingleObject3 getInstance() {
        if (instance == null) {
            synchronized (SingleObject3.class) {
                if (instance == null) {
                    instance = new SingleObject3();
                }
            }
        }
        return instance;
    }
}

class SingleObject4 {
    private SingleObject4() {
    }

    private static class innerHolder {
        private static final SingleObject4 instance = new SingleObject4();
    }

    public static final SingleObject4 getInstance() {
        return innerHolder.instance;
    }
}
