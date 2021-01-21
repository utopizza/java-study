package competition;

import java.io.*;
import java.util.*;

public class JingDong {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String inputPath = "query.txt";
        String outputPath = "result.txt";
        File inputFile = null, outputFile = null;

        // read file
        try {
            inputFile = new File(inputPath);
            if (inputFile.isFile() && inputFile.exists()) {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(inputFile), "UTF-8");
                BufferedReader bufferReader = new BufferedReader(reader);
                String line = null;
                while ((line = bufferReader.readLine()) != null) {
                    if (line.trim().equals("")) continue;
                    String[] tuple = line.split("  ");
                    if (tuple.length != 2) throw new RuntimeException("Invalid input.");
                    if (map.containsKey(tuple[0])) map.put(tuple[0], map.get(tuple[0]) + Integer.parseInt(tuple[1]));
                    else map.put(tuple[0], Integer.parseInt(tuple[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // sort and get text
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        String text = "";
        for (Map.Entry<String, Integer> entry : list) {
            text += (entry.getKey() + "\t" + entry.getValue() + "\r\n");
        }
//        Map.Entry<String, Integer>[] arr = sortEntry(map);
//        String text = "";
//        for (Map.Entry<String, Integer> entry : arr) {
//            text += (entry.getKey() + "\t" + entry.getValue() + "\r\n");
//        }

        // write file
        try {
            outputFile = new File(outputPath);
            outputFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(text);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map.Entry<String, Integer>[] sortEntry(Map map) {
        Map.Entry<String, Integer>[] arr = (Map.Entry<String, Integer>[]) map.entrySet().toArray();
        sort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void sort(Map.Entry<String, Integer>[] arr, int lo, int hi) {
        if (lo >= hi) return;
        int pivot = partition(arr, lo, hi);
        sort(arr, lo, pivot - 1);
        sort(arr, pivot + 1, hi);
    }

    private static int partition(Map.Entry<String, Integer>[] arr, int lo, int hi) {
        int pivot = lo;
        int i = lo + 1, j = hi;
        while (true) {
            while (i < hi && arr[i].getValue() > arr[pivot].getValue()) i++;
            while (j > lo && arr[j].getValue() < arr[pivot].getValue()) j--;
            if (i >= j) break;
            swap(arr, i, j);
        }
        swap(arr, pivot, j);
        return j;
    }

    private static void swap(Map.Entry<String, Integer>[] arr, int i, int j) {
        Map.Entry<String, Integer> temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}