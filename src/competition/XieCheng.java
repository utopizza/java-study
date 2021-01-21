package competition;

import java.util.*;

public class XieCheng {
}

class XCMain {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long x = in.nextLong();
        int count = 0;
        while (x != 0) {
            x = x & (x - 1);
            count++;
        }
        System.out.println(count);
    }
}

class XCMain2 {
    private static class Record {
        int id;
        int start;
        int end;

        Record(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        int target = in.nextInt();
        Record[] arr = new Record[count];
        for (int i = 0; i < count; i++) {
            arr[i] = new Record(in.nextInt(), in.nextInt(), in.nextInt());
        }
//        Arrays.sort(arr, new Comparator<Record>() {
//            @Override
//            public int compare(Record o1, Record o2) {
//                return o1.start - o2.start;
//            }
//        });
//
//        int lo = 0, hi = arr.length - 1;
//        while (lo < hi) {
//            int mid = (lo + hi + 1) / 2;
//            if (arr[mid].start <= target) lo = mid;
//            else hi = mid - 1;
//        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (arr[i].start <= target && target <= arr[i].end) {
                result.add(arr[i].id);
            }
        }

        if (result.size() == 0) System.out.println("null");
        else {
            Collections.sort(result);
            for (int res : result) {
                System.out.println(res);
            }
        }

    }
}

class XCMain3 {

    private static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private int MAX_ENTRIES;

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }

        LRUCache(int MAX_ENTRIES) {
            super(MAX_ENTRIES, 0.75f, true);
            this.MAX_ENTRIES = MAX_ENTRIES;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = Integer.parseInt(in.nextLine());
        LRUCache<Integer, Integer> cache = new LRUCache<>(count);
        List<Integer> result = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            if (line[0].equals("p")) {
                cache.put(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
            } else {
                Integer object = cache.get(Integer.parseInt(line[1]));
                if (object == null) result.add(-1);
                else result.add(object);
            }
        }
        for (int res : result)
            System.out.println(res);
    }

}