package algorithm;

public class Sort {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 1, 1, 2, 3, 2, 6, 5, 8};
        new Count().sort(arr);
        print(arr);
    }

    public static void print(int[] arr) {
        for (int n : arr) {
            System.out.print(n + " ");
        }
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

class Insertion extends Sort {
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j >= 1 && arr[j] < arr[j - 1]; j--) {
                swap(arr, j, j - 1);
            }
        }
    }
}

class Shell extends Sort {
    public void sort(int[] arr) {
        int h = 1;
        while (3 * h < arr.length) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < arr.length; i++) {
                for (int j = i; j >= h && arr[j] < arr[j - h]; j -= h) {
                    swap(arr, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}

class Selection extends Sort {
    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            swap(arr, i, min);
        }
    }
}

class Bubble extends Sort {
    public void sort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
}

class Quick extends Sort {

    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int lo, int hi) {
        if (lo >= hi) return;
        int pivot = partition(arr, lo, hi);
        sort(arr, lo, pivot - 1);
        sort(arr, pivot + 1, hi);
    }

    private int partition(int[] arr, int lo, int hi) {
        int pivot = lo;
        int i = lo + 1, j = hi;
        while (true) {
            while (i < hi && arr[i] < arr[pivot]) i++;
            while (j > lo && arr[j] > arr[pivot]) j--;
            if (i >= j) break;
            swap(arr, i, j);
        }
        swap(arr, pivot, j); // arr[j]<=arr[pivot]
        return j;
    }
}

class Merge extends Sort {
    private int[] helper;

    public void sort(int[] arr) {
        helper = new int[arr.length];
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2; // mid=(lo+hi)/2, so that mid is in the left sub array
        sort(arr, lo, mid);           // from lo to mid
        sort(arr, (mid + 1), hi);     // from mid+1 to hi
        merge(arr, lo, mid, hi);      // from lo to mid, from mid+1 to hi
    }

    private void merge(int[] arr, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) helper[k] = arr[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) arr[k] = helper[j++];
            else if (j > hi) arr[k] = helper[i++];
            else if (helper[i] < helper[j]) arr[k] = helper[i++];
            else arr[k] = helper[j++];
        }
    }
}

class Heap extends Sort {

    private int[] helper;

    public void sort(int[] arr) {
        // let index starting from 1 rather than 0
        helper = new int[arr.length + 1];
        for (int i = 1; i < helper.length; i++) helper[i] = arr[i - 1];

        // sort
        int len = helper.length - 1;
        for (int i = helper.length / 2; i > 1; i--) sink(helper, i, len); // build heap
        for (int i = helper.length; i > 1; i--) {                         // sort and destroy heap
            swap(helper, 1, len);
            sink(helper, 1, --len);
        }

        // move back to arr
        for (int i = 1; i < helper.length; i++) arr[i - 1] = helper[i];
    }

    private void sink(int[] arr, int k, int len) {
        while (k * 2 <= len) {
            int child = k * 2;
            if (child + 1 <= len && arr[child + 1] > arr[child]) child++; //choose the bigger child
            if (arr[k] < arr[child]) swap(arr, k, child);
            k = child;
        }
    }
}

class Count extends Sort {
    int k = 100;
    int[] counter;
    int[] sorted;

    public void sort(int[] arr) {
        counter = new int[k];
        sorted = new int[arr.length];
        for (int i = 0; i < arr.length; i++) counter[arr[i]]++;
        for (int i = 1; i < counter.length; i++) counter[i] = counter[i - 1] + counter[i];
        for (int i = 0; i < arr.length; i++) {
            sorted[counter[arr[i]] - 1] = arr[i];
            counter[arr[i]]--;
        }
        for (int i = 0; i < arr.length; i++) arr[i] = sorted[i];
    }
}