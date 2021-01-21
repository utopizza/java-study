package competition;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TengXun {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        int a = in.nextInt(), x = in.nextInt();
        int b = in.nextInt(), y = in.nextInt();
        int sum = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i * a + j * b == k) {
                    sum += (C(x) / (C(i) * C(x - i)) * C(y) / (C(j) * C(y - j)));
                }
            }
        }
        System.out.println(sum);
    }

    private static int C(int n) {
        if (n <= 1) return 1;
        else return n * C(n - 1);
    }
}

class TXMain2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt();
        Set<Integer>[] inDegree = new Set[n + 1], outDegree = new Set[n + 1];
        for (int i = 0; i < inDegree.length; i++) inDegree[i] = new HashSet<>();
        for (int i = 0; i < inDegree.length; i++) outDegree[i] = new HashSet<>();
        for (int i = 1; i <= m; i++) {
            int u = in.nextInt(), v = in.nextInt();
            if (u == v) continue;
            outDegree[u].add(v);
            inDegree[v].add(u);
        }

        for (int i = 1; i <= n; i++) dfs(outDegree, i, i, new boolean[n + 1]);
        for (int i = 1; i <= n; i++) dfs(inDegree, i, i, new boolean[n + 1]);
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (inDegree[i].size() > outDegree[i].size()) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void dfs(Set<Integer>[] degree, int sourceCity, int curCity, boolean[] marked) {
        if (curCity != sourceCity) degree[sourceCity].add(curCity);
        marked[curCity] = true;
        for (int nextCity : degree[curCity]) {
            if (marked[nextCity] == false) {
                dfs(degree, sourceCity, nextCity, marked);
            }
        }
    }
}

class TXMain3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        String[] result = new String[count];
        for (int i = 0; i < result.length; i++) {
            int a = in.nextInt(), b = in.nextInt(), c = in.nextInt();
            result[i] = "NO";
        }
        for (int i = 0; i < result.length; i++) System.out.println(result[i]);
    }
}
