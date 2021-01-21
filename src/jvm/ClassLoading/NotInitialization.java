package jvm.ClassLoading;

public class NotInitialization {
    public static void main(String[] args) {

        // 使用一个静态变量，只有直接定义该变量的类才会被初始化
        System.out.println(SubClass.super_value);

        // 通过数组定义来引用类，不会触发此类的初始化
        SubClass[] arr = new SubClass[10];

        // 常量在编译阶段会通过“常量传播优化”，将ConstantClass.constant_value直接存储到NotInitialization的常量池
        System.out.println(ConstantClass.constant_value);

    }
}

class SuperClass {
    static {
        System.out.println("SuperClass init.");
    }

    public static int super_value = 123;
}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init.");
    }

    public static int sub_value = 456;
}

class ConstantClass {
    static {
        System.out.println("ConstantClass init.");
    }

    public static final String constant_value = "abc";
}