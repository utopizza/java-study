package competition;

import java.util.Scanner;

public class Qihu360 {
}

class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long top = Long.MIN_VALUE, left = Long.MAX_VALUE, right = Long.MIN_VALUE, bottom = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            long x = in.nextInt();
            long y = in.nextInt();
            top = Math.max(top, y);
            bottom = Math.min(bottom, y);
            left = Math.min(left, x);
            right = Math.max(right, x);
        }
        long a = Math.abs(top - bottom);
        long b = Math.abs(left - right);
        long c = Math.max(a, b);
        System.out.println(c * c);
    }
}

class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt();

        int[] look = new int[n + 1];
        for (int i = 1; i <= n; i++) look[i] = in.nextInt();

        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            int cnt = 0;
            boolean[] set = new boolean[m + 1];
            for (int j = i; j <= n; j++) {
                if (set[look[j]] == false) {
                    set[look[j]] = true;
                    cnt++;
                }
                dp[i][j] = cnt;
            }
        }

        int q = in.nextInt();
        int[] result = new int[q];
        for (int i = 0; i < q; i++) result[i] = dp[in.nextInt()][in.nextInt()];

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}

class Main3 {
    public static void main(String[] args) {

    }
}