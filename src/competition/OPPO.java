package competition;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class OPPO {
    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = new int[10];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            int r = random.nextInt(100);
            while (set.contains(r)) r = random.nextInt();
            arr[i] = r;
        }
        for (int i = 0; i < arr.length; i++) arr[i] = reverse(arr[i]);
        Arrays.sort(arr);
        int i = 0, j = arr.length - 1;
        while (i < j) {
            swap(arr, i++, j--);
        }
        for (int x : arr) System.out.println(x);
    }

    private static int reverse(int x) {
        int result = 0;
        while (x != 0) {
            result = result * 10 + x % 10;
            x = x / 10;
        }
        return result;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
