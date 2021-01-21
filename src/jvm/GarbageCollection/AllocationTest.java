package jvm.GarbageCollection;

// -verbose:gc -Xms20M -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
public class AllocationTest {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        //testAllocation();
        //testPretenureSizeThreshold();
        //testTenuringThreshold();
        testHandlePromotion();
    }

    //
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB]; // Minor GC
    }

    // -XX:PretenureSizeThreshold=3145728
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[6 * _1MB]; // allocated to old gen
    }

    // -XX:MaxTenuringThreshold=1
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    // -XX:HandlePromotionFailure
    public static void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }
}
