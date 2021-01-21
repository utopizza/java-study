package learnJdk;

import java.util.*;
import java.util.concurrent.*;

public class LearnJDK {
    public static void main(String[] args) {
    }

    private static void jdk() {
        // list
        ArrayList<Integer> arrayList = new ArrayList<>();
        Vector<Integer> vector = new Vector<>();
        CopyOnWriteArrayList<Integer> cowArrayList = new CopyOnWriteArrayList();
        ConcurrentLinkedQueue<String> conQueue = new ConcurrentLinkedQueue<>();
        PriorityQueue<Integer> priorityQueue;

        // map
        HashMap<String, Integer> hashMap = new HashMap<>();
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        WeakHashMap<String, Integer> weakHashMap = new WeakHashMap<>();
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        // future
        Future<Integer> future;
        ThreadPoolExecutor threadPoolExecutor;
    }
}
