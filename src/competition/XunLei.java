package competition;

import java.util.Scanner;

public class XunLei {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println(getGouGuNum(n));
    }

    private static int gcd(int x, int y) {
        if (y == 0) return x;
        return gcd(y, x % y);
    }

    private static int getGouGuNum(int num) {
        int cnt = 0;
        int limit = (int) Math.sqrt(num);
        int a = 0, b = 0, c = 0;
        for (int s = 1; s <= limit; s++) {
            for (int r = s + 1; r <= limit; r += 2) {
                c = r * r + s * s;
                if (gcd(r, s) == 1 && c <= num) {
                    a = r * r - s * s;
                    b = 2 * r * s;
                    cnt++;
                }
            }
        }
        return cnt;
    }
}

class XunLei2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt(), b = in.nextInt();
        System.out.println(getMax(a, b));
    }

    private static int getMax(int A, int B) {
        if (6 * A + 1 * B < 0) return 2 * B + 15 * A;
        if (5 * A + 2 * B < 0) return 4 * B + 13 * A;
        if (4 * A + 3 * B < 0) return 6 * B + 11 * A;
        if (3 * A + 4 * B < 0) return 8 * B + 9 * A;
        if (2 * A + 5 * B < 0) return 11 * B + 6 * A;
        if (1 * A + 6 * B < 0) return 14 * B + 3 * A;
        return 17 * B;
    }
}