package jvm.RuntimeMemory;

//-XX:PermSize=10M -XX:MaxPermSize=10M
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        // jdk1.6 will OOM but jdk1.7 won't
//        List<String> list = new ArrayList<String>();
//        int i = 0;
//        while (true) {
//            list.add(String.valueOf(i++).intern());
//        }

        // intern() after jdk1.7
        String str1 = new StringBuilder("a").append("b").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

    }
}
