package competition;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DiDi {
}

class DDMain {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList("a", "b", "c"));
        for (String x : set) System.out.println(x);
    }
}