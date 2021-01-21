package competition;

import java.util.*;

public class MeiTuan {
}

class MT1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        List<Integer>[] arr = new ArrayList[count + 1];
        for (int i = 0; i < arr.length; i++) arr[i] = new ArrayList<>();
        for (int i = 1; i <= count - 1; i++) {
            int root = in.nextInt();
            int child = in.nextInt();
            arr[root].add(child);
        }
        int height = getHeight(arr);
        System.out.println((count - 1 - height) * 2 + height);
    }

    private static int getHeight(List<Integer>[] arr) {
        int level = 0;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addAll(arr[1]);
        while (queue.size() > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int cur = queue.removeFirst();
                queue.addAll(arr[cur]);
            }
            level++;
        }
        return level;
    }
}

class MT2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), k = in.nextInt(), t = in.nextInt();
        int[] arr = new int[n + 1];
        for (int i = 1; i <= n; i++) arr[i] = in.nextInt();
        System.out.println(getCount(arr, k, t));
    }

    private static int getCount(int[] arr, int k, int t) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i <= 1 + k - 1; i++) {
            int v = arr[i];
            if (map.containsKey(v)) map.put(v, map.get(v) + 1);
            else map.put(v, 1);
        }
        for (int c : map.values()) {
            if (c >= t) {
                count++;
                break;
            }
        }
        for (int l = 1; l + k - 1 < arr.length - 1; l++) {
            int r = l + k;
            map.put(arr[l], map.get(arr[l]) - 1);
            if (map.containsKey(arr[r])) map.put(arr[r], map.get(arr[r]) + 1);
            else map.put(arr[r], 1);

            for (int c : map.values()) {
                if (c >= t) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}