package interviewCode;

import java.util.LinkedList;
import java.util.Stack;

public class StackAndQueue {
    public static void main(String[] args) {
        new MaxWindow().testCase();
    }
}

class GetMinStack {

    Stack<Integer> stackData, stackMin;

    public GetMinStack() {
        stackData = new Stack<Integer>();
        stackMin = new Stack<Integer>();
    }

    public void push(int x) {
        if (stackMin.isEmpty() || x <= stackMin.peek()) stackMin.push(x);
        stackData.push(x);
    }

    public int pop() {
        if (stackData.isEmpty()) throw new RuntimeException("stack is empty.");
        if ((int) stackMin.peek() == (int) stackData.peek()) stackMin.pop();
        return stackData.pop();
    }

    public int getMin() {
        if (stackData.isEmpty()) throw new RuntimeException("stack is empty.");
        return stackMin.peek();
    }
}

class QueueByStack {
    Stack<Integer> stack1, stack2;

    public QueueByStack() {
        stack1 = new Stack<Integer>();
        stack2 = new Stack<Integer>();
    }

    public void add(int x) {
        stack1.push(x);
    }

    public int poll() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) throw new RuntimeException("queue is empty.");
        return stack2.pop();
    }

    public int peek() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) throw new RuntimeException("queue is empty.");
        return stack2.peek();
    }
}

class ReverseStack {
    public int getAndRemoveBottom(Stack<Integer> stack) {
        if (stack.size() == 1) return stack.pop();
        else {
            int cur = stack.pop();
            int bottom = getAndRemoveBottom(stack);
            stack.push(cur);
            return bottom;
        }
    }

    public void reverseStack(Stack<Integer> stack) {
        if (stack.isEmpty()) return;
        else {
            int bottom = getAndRemoveBottom(stack);
            reverseStack(stack);
            stack.push(bottom);
        }
    }

    public void testCase() {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        reverseStack(stack);
        while (!stack.isEmpty())
            System.out.println(stack.pop());
    }
}

class SortStackByStack {
    public void sort(Stack<Integer> stack) {
        Stack<Integer> helper = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            while (!helper.isEmpty() && helper.peek() < cur) stack.push(helper.pop());
            helper.push(cur);
        }
        while (!helper.isEmpty()) stack.push(helper.pop());
    }

    public void testCase() {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(4);
        stack.push(2);
        stack.push(3);
        stack.push(5);
        stack.push(1);
        sort(stack);
        while (!stack.isEmpty())
            System.out.println(stack.pop());
    }
}

class MaxWindow {
    public int[] getMaxWindow(int[] arr, int winLen) {
        if (arr.length == 0 || arr.length < winLen || winLen < 1) return null;
        int[] maxArr = new int[arr.length - winLen + 1];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            // delete the elements which are smaller than arr[i] as well as in front of arr[i]
            while (queue.size() > 0 && arr[queue.getLast()] <= arr[i]) {
                queue.removeLast();
            }
            // enqueue arr[i]. Now there is no smaller element before arr[i]. queue is descending.
            queue.add(i);
            if (i - queue.getFirst() + 1 > winLen) queue.removeFirst();
            if (i >= winLen - 1) maxArr[index++] = arr[queue.getFirst()];
        }
        return maxArr;
    }

    public void testCase() {
        int[] arr = new int[]{4, 3, 5, 4, 3, 3, 6, 7};
        int[] maxArr = getMaxWindow(arr, 3);
        for (int x : maxArr) System.out.println(x);
    }
}

class HanoiProblem {
    public void solve(int count, String a, String b, String c) {
        if (count == 1) System.out.println(a + "->" + c); // last one : a -> c
        else {
            solve(count - 1, a, c, b); // first a -> b
            System.out.println(a + "->" + c);
            solve(count - 1, b, a, c); // then b -> c
        }
    }

    public void testCase() {
        solve(3, "a", "b", "c");
    }
}

class MaxRectangleSize {
    public int maxRecSize(int[][] map) {
        if (map.length == 0 || map[0].length == 0) return 0;
        int row = map.length, col = map[0].length, maxArea = 0;
        int[] height = new int[col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                height[j] = map[i][j] == 0 ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxArea, maxRecFromBottom(height));
        }
        return maxArea;
    }

    private int maxRecFromBottom(int[] height) {
        if (height.length == 0) return 0;
        int maxArea = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }
}

class MaxAndMinArray {
    public int getCount(int[] arr, int diff) {
        if (arr == null || arr.length == 0) return 0;
        LinkedList<Integer> qmin = new LinkedList<Integer>(), qmax = new LinkedList<Integer>();
        int i = 0, j = 0, count = 0;
        while (i < arr.length) {
            while (j < arr.length) {
                // min queue
                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) qmin.pollLast();
                qmin.addLast(j);

                // max queue
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]) qmax.pollLast();
                qmax.addLast(j);

                // max-min>diff
                if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > diff) break;
                else j++;
            }
            if (qmin.peekFirst() == i) qmin.pollFirst();
            if (qmax.peekFirst() == i) qmax.pollFirst();
            count += j - i;
            i++;
        }
        return count;
    }
}