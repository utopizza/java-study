package jvm.ClassLoading;

public class IllegalForwardReference {
    static {
        i = 2;
        //System.out.println(i);
    }

    static int i = 1;
}

