package competition;

import java.util.Scanner;
import java.util.Stack;

public class Zhihu {
}

class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Stack<Character> stack = new Stack<>();
        stack.push('2');
        for (int i = 2; i <= N; i++) {
            char top = stack.pop();
            if (top == '2') stack.push('3');
            else {
                int count = 1;
                while (!stack.isEmpty() && stack.peek() == '3') {
                    stack.pop();
                    count++;
                }
                if (stack.isEmpty()) stack.push('2');
                else {
                    stack.pop();
                    stack.push('3');
                }
                for (int j = 0; j < count; j++) stack.push('2');
            }
        }
        String res = "";
        for (char c : stack) res += c;
        System.out.println(res);
    }
}
