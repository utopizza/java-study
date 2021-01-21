package competition;

import java.util.Scanner;
import java.util.Stack;

public class Moni {

    public static void main1(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a1 = sc.nextInt(), a2 = sc.nextInt(), a3 = sc.nextInt();
        int b1 = sc.nextInt(), b2 = sc.nextInt(), b3 = sc.nextInt();
        int[] cards = new int[14];
        for (int i = 1; i <= 13; i++) cards[i] = 4;
        cards[a1]--;
        cards[a2]--;
        cards[a3]--;
        cards[b1]--;
        cards[b2]--;
        cards[b3]--;
        int target = b1 + b2 + b3 - a1 - a2 - a3;
        double ans = winCard(cards, target);
        System.out.println(String.format("%.4f", ans));
    }

    private static double winCard(int[] cards, int target) {
        if (target > 12) return 0;
        if (target < -12) return 1;
        double count = 0;
        for (int i = 1; i <= 13; i++) {
            for (int j = 1; j < i - target; j++) {
                count += cards[j] * cards[i];
            }
        }
        return count / (46 * 45);
    }

    public static void main2(String[] args) {
        Scanner sc = new Scanner(System.in);
        int starsCount = sc.nextInt();
        int[][] stars = new int[starsCount][2];
        for (int i = 0; i < stars.length; i++) {
            stars[i][0] = sc.nextInt();
            stars[i][1] = sc.nextInt();
        }
        int questCount = sc.nextInt();
        int[][] quest = new int[questCount][4];
        for (int i = 0; i < quest.length; i++) {
            for (int j = 0; j < 4; j++) {
                quest[i][j] = sc.nextInt();
            }
        }

        for (int i = 0; i < quest.length; i++) {
            int count = countStars(stars, quest[i][0], quest[i][1], quest[i][2], quest[i][3]);
            System.out.println(count);
        }
    }

    private static int countStars(int[][] stars, int a1, int b1, int a2, int b2) {
        int res = 0;
        for (int i = 0; i < stars.length; i++) {
            int x = stars[i][0], y = stars[i][1];
            if (a1 <= x && x <= a2 && b1 <= y && y <= b2)
                res++;
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int len = in.nextInt();
        long[] arr = new long[len];
        for (int i = 0; i < len; i++)
            arr[i] = in.nextLong();
        System.out.println(operation(arr));
    }

    private static int operation(long[] arr) {
        int count = 0;
        for (long num : arr) {
            while ((num & 1) == 0) {
                count++;
                num = num >> 1;
            }
        }
        return count;
    }

    public static void main4(String[] args) {
        Scanner in = new Scanner(System.in);
        long a = in.nextLong();
        long b = in.nextLong();

        double cmp = 0;
        if (a == 1 || b == 1) cmp = a - b;
        else cmp = b / a * Math.log(a) - Math.log(b);
        String cmpStr = "";

        if (cmp == 0) cmpStr = "=";
        else if (cmp < 0) cmpStr = "<";
        else cmpStr = ">";
        System.out.println(cmpStr);
    }

    public static void main5(String[] args) {
        Scanner in = new Scanner(System.in);
        int len = Integer.parseInt(in.nextLine());
        String[] arr = new String[len];
        for (int i = 0; i < arr.length; i++)
            arr[i] = in.nextLine();
        System.out.println(match(arr));
    }

    private static int match(String[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i == j) continue;
                if (canMatch(arr[i] + arr[j])) count++;
                if (canMatch(arr[i] + arr[i] + arr[j])) count++;
            }
        }
        return count;
    }

    private static boolean canMatch(String str) {
        Stack<Character> st = new Stack<Character>();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c == '(') st.push(c);
            else {
                if (st.isEmpty()) return false;
                else {
                    char x = st.pop();
                    if (x != '(') return false;
                }
            }
        }
        if (st.size() > 0) return false;
        return true;
    }

}
