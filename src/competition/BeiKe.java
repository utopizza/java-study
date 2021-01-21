package competition;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BeiKe {
}

class bkQ1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int m = in.nextInt();
            min = Math.min(min, m);
            sum += m;
        }
        System.out.println(sum - min);
    }
}

class bkQ2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Node> list = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            int begin = in.nextInt(), end = in.nextInt();

        }
    }

    private static class Node {
        int id;
        int begin;
        int end;

        Node(int id, int begin, int end) {
            this.id = id;
            this.begin = begin;
            this.end = end;
        }
    }
}

class bkQ3 {
    public static void main(String[] args) {

    }
}