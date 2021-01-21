package jvm.ClassLoading;

public class FieldResolution {
    interface Interface0 {
        int a = 0;
    }

    interface Interface1 extends Interface0 {
        int a = 1;
    }

    interface Interface2 {
        int a = 3;
    }

    static class Parent implements Interface1 {
        public static int a = 3;
    }

    static class Sub extends Parent implements Interface2 {
        // 如果注释这行，则会拒绝编译，因为接口与父类同时存在字段 a
        public static int a = 4;
    }

    public static void main(String[] args) {
        System.out.println(Sub.a);
    }
}
