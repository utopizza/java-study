package jvm.ExecuteEngine;

public class StaticDispatch {

    static abstract class Human {
    }

    static class Man extends Human {
    }

    static class Woman extends Human {
    }

    public void sayHello(Human guy) {
        System.out.println("hello guy");
    }

    public void sayHello(Man guy) {
        System.out.println("hello man");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello woman");
    }

    // 在同一个类内的同名方法的选择称为重载，通过方法的参数类型、顺序、数量来区分并调用不同的方法
    // 此调用过程称为静态分派，因为在编译期即可根据参数类型、顺序、数量来确定选择哪个版本的方法
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(woman);
    }
}
