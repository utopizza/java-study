package jvm.RuntimeMemory;

import java.util.ArrayList;
import java.util.List;

//-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
public class JavaHeapOOM {
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }

    static class OOMObject {
    }
}
