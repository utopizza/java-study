package competition;

import java.util.*;

public class DianXin {
}

class DXMain {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) map.put(c, map.get(c) + 1);
            else map.put(c, 1);
        }

        int min = Integer.MAX_VALUE;
        for (int count : map.values()) min = Math.min(min, count);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (map.get(c) != min) {
                sb.append(c);
            }
        }

        System.out.println(sb.toString());
    }
}

class DXMain2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int index = in.nextInt();
        int t2 = 0, t3 = 0, t5 = 0;
        int[] arr = new int[index];
        arr[0] = 1;
        for (int i = 1; i < index; i++) {
            arr[i] = Math.min(arr[t2] * 2, Math.min(arr[t3] * 3, arr[t5] * 5));
            if (arr[i] == arr[t2] * 2) t2++;
            if (arr[i] == arr[t3] * 3) t3++;
            if (arr[i] == arr[t5] * 5) t5++;
        }
        System.out.println(arr[index - 1]);
    }
}

class DXMain3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] line = in.nextLine().trim().split(" ");
        String str1 = line[0], str2 = line[line.length - 1];
        if (line.length <= 1 || str1.length() == 0 || str2.length() == 0) System.out.println(0);
        else {
            int max = 0;
            int len = Math.min(str1.length(), str2.length());
            for (int i = 1; i <= len; i++) {
                String s1 = str1.substring(str1.length() - i);
                String s2 = str2.substring(0, i);
                if (s1.equals(s2)) max = Math.max(max, i);
            }
            System.out.println(max);
        }
    }
}