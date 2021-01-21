package competition;

import java.util.Scanner;

public class Ali {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] firstLine = in.nextLine().split(",");
        int a = Integer.parseInt(firstLine[0]), b = Integer.parseInt(firstLine[1]);
        String[] secondLine = in.nextLine().split(",");
        int count = secondLine.length / 2;
        int[] polyX = new int[count], polyY = new int[count];
        for (int i = 0; i < count; i++) {
            polyX[i] = Integer.parseInt(secondLine[i * 2]);
            polyY[i] = Integer.parseInt(secondLine[i * 2 + 1]);
        }
        boolean flag = isIn(count, polyX, polyY, a, b);
        if (flag) System.out.println("yes,0");
        else System.out.println("no,0");
    }

    public static boolean isIn(int polySides, int[] polyX, int[] polyY, int x, int y) {
        boolean isIn = false;
        int j = polySides - 1;
        for (int i = 0; i < polySides; i++) {
            if (polyY[i] < y && polyY[j] >= y || polyY[j] < y && polyY[i] >= y) {
                if (polyX[i] + (y - polyY[i]) / (polyY[j] - polyY[i]) * (polyX[j] - polyX[i]) < x) {
                    isIn = !isIn;
                }
            }
            j = i;
        }
        return isIn;
    }
}
