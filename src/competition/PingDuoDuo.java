package competition;

import java.util.Arrays;
import java.util.Scanner;

public class PingDuoDuo {
    public static void main(String[] args) {
    }
}

class pddQ1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        int getMan = buyCoke(count);
        String name = "";
        if (getMan == 1) name = "Alice";
        else if (getMan == 2) name = "Bob";
        else if (getMan == 3) name = "Cathy";
        else name = "Dave";
        System.out.println(name);
    }

    private static int buyCoke(int count) {
        if (count <= 4) return count;
        int sum = 4;
        int row = 1;
        int rowCount = 4;
        while (sum < count) {
            rowCount = rowCount << 1;
            sum = sum + rowCount;
            row++;
        }

        int curRowCount = rowCount;
        int lastSum = sum - curRowCount;
        int curIndex = count - lastSum;
        int a = curRowCount >> 2, b = a << 1, c = a * 3;

        if (curIndex <= a) return 1;
        else if (curIndex <= b) return 2;
        else if (curIndex <= c) return 3;
        else return 4;
    }
}

class pddQ2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] strs = in.nextLine().split(" ");
        if (strs.length == 0) {
            System.out.println(0);
            return;
        }
        int[] arr = new int[strs.length];
        for (int i = 0; i < arr.length; i++) arr[i] = Integer.parseInt(strs[i]);
        Arrays.sort(arr);
        int carsSize = getCars(arr);
        System.out.println(carsSize);
    }

    private static int getCars(int[] arr) {
        return 1;
    }
}