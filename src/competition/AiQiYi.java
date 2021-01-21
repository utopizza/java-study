package competition;

import java.util.Arrays;
import java.util.Scanner;

public class AiQiYi {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();

        int[] arr1 = new int[3], arr2 = new int[3];
        for (int i = 0; i < arr1.length; i++) arr1[i] = str.charAt(i) - '0';
        for (int i = 0; i < arr2.length; i++) arr2[i] = str.charAt(i + 3) - '0';

        System.out.println(getCount(arr1, arr2));
    }

    private static int getCount(int[] arr1, int[] arr2) {
        int sum1 = arr1[0] + arr1[1] + arr1[2], sum2 = arr2[0] + arr2[1] + arr2[2];
        int[] larger, smaller;

        int diff = 0;
        if (sum1 == sum2) return 0;
        else if (sum1 > sum2) {
            larger = arr1;
            smaller = arr2;
            diff = sum1 - sum2;
        } else {
            larger = arr2;
            smaller = arr1;
            diff = sum2 - sum1;
        }

        int[] changeable = new int[6];
        for (int i = 0; i < changeable.length; i++) {
            if (i < 3) changeable[i] = larger[i];
            else changeable[i] = 9 - smaller[i - 3];
        }
        Arrays.sort(changeable);

        int count = 0;
        int i = 5;
        while (diff > 0) {
            diff -= changeable[i];
            i--;
            count++;
        }
        return count;
    }
}
