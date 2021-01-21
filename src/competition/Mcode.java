package competition;

import java.util.Scanner;

public class Mcode {

    public static void main1(String[] args) {
        String[][] keys = new String[][]{{"", "ABC", "DEF"}, {"GHI", "JKL", "MNO"}, {"PQRS", "TUV", "WXYZ"}};
        Scanner in = new Scanner(System.in);
        int lineCount = Integer.parseInt(in.nextLine());
        String[] names = new String[lineCount];
        for (int i = 0; i < lineCount; i++) {
            names[i] = in.nextLine();
        }
        for (int i = 0; i < names.length; i++) {
            int moveStep = move(names[i], keys);
            System.out.println(moveStep);
        }

    }

    public static int move(String name, String[][] keys) {
        char[] arr = name.toCharArray();
        int step = 0, curX = 0, curY = 0;
        for (int k = 0; k < arr.length; k++) {
            char c = arr[k];
            int x = 0, y = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (keys[i][j].contains(String.valueOf(c))) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }
            step += Math.abs(curX - x) + Math.abs(curY - y);
            curX = x;
            curY = y;
        }
        return step;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int cnt = Integer.parseInt(in.nextLine());
        int[] a = new int[cnt];
        int[] b = new int[cnt];
        for(int i=0;i<a.length;i++) a[i]=in.nextInt();
        for(int i=0;i<b.length;i++) b[i]=in.nextInt();

    }

}
