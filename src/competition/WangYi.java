package competition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WangYi {
    public static void main(String[] args) {
    }
}

class wyQ1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), k = in.nextInt();
        int[] arr = new int[n];
        int result = 0;
        int[] sum = new int[n];
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) arr[i] = in.nextInt();
        for (int i = 0; i < n; i++) {
            int wake = in.nextInt();
            if (wake == 1) {
                result += arr[i];
                arr[i] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            sum[i] = (i > 0 ? sum[i - 1] : 0) + arr[i];
            max = Math.max(max, sum[i] - (i >= k ? sum[i - k] : 0));
        }

        System.out.println(result + max);
    }
}

class wyQ2 {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // apples
        int n = Integer.parseInt(in.nextLine());
        String[] arr = in.nextLine().split(" ");
        int[] apples = new int[n];
        for (int i = 0; i < n; i++) apples[i] = Integer.parseInt(arr[i]);

        // query
        int queryCount = Integer.parseInt(in.nextLine());
        String[] arr2 = in.nextLine().split(" ");
        int[] query = new int[queryCount];
        for (int i = 0; i < queryCount; i++) query[i] = Integer.parseInt(arr2[i]);

        // result
        List<Integer> results = doQuery(apples, query);
        for (int result : results) System.out.println(result);
    }

    private static List<Integer> doQuery(int[] apples, int[] query) {
        int[] arr = new int[apples.length];
        arr[0] = apples[0];
        for (int i = 1; i < apples.length; i++) arr[i] = arr[i - 1] + apples[i];
        List<Integer> list = new ArrayList<>();
        for (int x : query) {
            int index = search(arr, x);
            list.add(index + 1);
        }
        return list;
    }

    private static int search(int[] arr, int x) {
        int lo = 0, hi = arr.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < x)
                lo = mid + 1;
            else
                hi = mid;
        }
        return lo;
    }
}

class wyQ3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        char[] arr = new char[n + m];
        for (int i = 0; i < n; i++) arr[i] = 'a';
        for (int i = 0; i < m; i++) arr[n + i] = 'z';
        List<List<Character>> result = permuteUnique(arr);
        List<Character> res = result.get(k - 1);
        String str = "";
        for (Character c : res) str += c;
        System.out.println(str);
    }

    private static List<List<Character>> permuteUnique(char[] chars) {
        List<List<Character>> soluList = new LinkedList<>();
        backtrack(soluList, new LinkedList<Character>(), chars, new boolean[chars.length]);
        return soluList;
    }

    private static void backtrack(List<List<Character>> soluList, List<Character> solu, char[] chars, boolean[] used) {
        if (solu.size() == chars.length) soluList.add(new LinkedList<>(solu));
        else {
            for (int i = 0; i < chars.length; i++) {
                if (used[i]) continue;
                if (i > 0 && chars[i - 1] == chars[i] && !used[i - 1]) continue;
                used[i] = true;
                solu.add(chars[i]);
                backtrack(soluList, solu, chars, used);
                solu.remove(solu.size() - 1);
                used[i] = false;
            }
        }
    }
}

class wfyQ1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        int result = 1;
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != str.charAt(i - 1)) {
                result = result + 1;
            } else {
                if (str.charAt(0) != str.charAt(i)) {
                    String str1 = new StringBuilder(str.substring(0, i)).reverse().toString();
                    String str2 = new StringBuilder(str.substring(i)).reverse().toString();
                    str = str1 + str2;
                    result = result + 1;
                }
            }
        }
        System.out.println(result);
    }
}