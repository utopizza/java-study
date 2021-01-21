package jvm.ExecuteEngine;

public class StaticResolution {

    // static、private、final修饰的方法在类加载的解析阶段就可以确定唯一调用版本
    public static void sayHello() {
        System.out.println("Hello.");
    }

    public static void main(String[] args) {
        // 因此在类加载的解析阶段，此处的 sayHello 方法的符号引用直接解析为直接引用
        sayHello();
    }
}
