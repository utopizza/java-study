package coding;

import java.util.*;

class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}

class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}

public class OfferCode {
    public static void main(String[] args) {
        //System.out.println(new StrToInt().StrToInt("  -1"));
        new hasPath().hasPath("ABCESFCSADEE".toCharArray(), 3, 4, "ABCCED".toCharArray());
    }
}

class Find {
    public boolean Find(int target, int[][] array) {
        if (array.length == 0 || array[0].length == 0) return false;
        int i = 0, j = array[0].length - 1;
        while (i < array.length && j >= 0) {
            if (array[i][j] == target) return true;
            else if (array[i][j] < target) i++;
            else j--;
        }
        return false;
    }
}

class printListFromTailToHead {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (listNode != null) {
            list.add(0, listNode.val);
            listNode = listNode.next;
        }
        return list;
    }
}

class reConstructBinaryTree {
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        return recursive(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    private TreeNode recursive(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd) {
        if (inStart > inEnd) return null;

        int root = pre[preStart];
        int index = inStart;
        while (index <= inEnd) {
            if (in[index] == root) break;
            index++;
        }
        int leftLen = index - inStart, rightLen = inEnd - index;

        TreeNode node = new TreeNode(root);
        node.left = recursive(pre, preStart + 1, preStart + leftLen, in, inStart, index - 1);
        node.right = recursive(pre, preStart + leftLen + 1, preEnd, in, index + 1, inEnd);
        return node;
    }
}

class implementQueueByTwoStack {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.size() == 0) {
            while (stack1.size() > 0) stack2.push(stack1.pop());
        }
        return stack2.pop();
    }
}

class minNumberInRotateArray {
    public int minNumberInRotateArray(int[] array) {
        if (array == null || array.length == 0) return 0;
        int lo = 0, hi = array.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (array[mid] <= array[0]) hi = mid;
            else lo = mid + 1;
        }
        return array[hi];
    }
}

class Fibonacci {
    public int Fibonacci(int n) {
        if (n <= 0) return 0;
        else if (n == 1) return 1;
        else {
            int[] dp = new int[n + 1];
            dp[0] = 0;
            dp[1] = 1;
            for (int i = 2; i <= n; i++)
                dp[i] = dp[i - 1] + dp[i - 2];
            return dp[n];
        }
    }
}

class JumpFloor {
    public int JumpFloor(int n) {
        if (n <= 0) return 0;
        else if (n == 1) return 1;
        else if (n == 2) return 2;
        else {
            int[] dp = new int[n + 1];
            dp[1] = 1;
            dp[2] = 2;
            for (int i = 3; i <= n; i++)
                dp[i] = dp[i - 1] + dp[i - 2];
            return dp[n];
        }
    }
}

class JumpFloorII {
    public int JumpFloorII(int n) {
        if (n <= 0) return 0;
        else if (n == 1) return 1;
        else {
            int[] dp = new int[n + 1];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                for (int j = 1; j <= i; j++) {
                    dp[i] += dp[i - j];
                }
            }
            return dp[n];
        }
    }
}

class RectCover {
    public int RectCover(int n) {
        if (n <= 0) return 0;
        else if (n == 1) return 1;
        else {
            int[] dp = new int[n + 1];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            return dp[n];
        }
    }
}

class NumberOf1 {
    public int NumberOf1(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = (n - 1) & n;
        }
        return count;
    }
}

class Power {
    public double Power(double b, int e) {
        if (b == 0) return 0;
        if (e == 0) return 1;
        if (e == 1) return b;
        if (e < 0) {
            b = 1 / b;
            e = 0 - e;
        }
        if (e % 2 == 0) return Power(b, e / 2) * Power(b, e / 2);
        else return b * Power(b, e / 2) * Power(b, e / 2);
    }
}

class reOrderArray {
    public void reOrderArray(int[] array) {
        if (array == null || array.length == 0) return;
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 1) == 1) {
                for (int j = i; j > index; j--) swap(array, j, j - 1);
                index++;
            }
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

class FindKthToTail {
    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k <= 0) return null;
        ListNode p = head;
        int count = k - 1;
        while (count > 0 && p != null && p.next != null) {
            p = p.next;
            count--;
        }
        if (p.next == null && count > 0) return null;
        while (p != null && p.next != null) {
            p = p.next;
            head = head.next;
        }
        return head;
    }
}

class ReverseList {
    public ListNode ReverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = ReverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public ListNode ReverseList2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode tempHead = new ListNode(0);
        tempHead.next = head;
        ListNode pre = head, p = head.next, q;
        while (p != null) {
            q = p.next;
            pre.next = q;
            p.next = tempHead.next;
            tempHead.next = p;
            p = q;
        }
        return tempHead.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int i = 1; i <= 5; i++) {
            p.next = new ListNode(i);
            p = p.next;
        }
        ListNode newHead = new ReverseList().ReverseList2(head);
        p = newHead;
        while (p != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }
    }
}

class HasSubtree {
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) return false;
        return recursive(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    private boolean recursive(TreeNode node1, TreeNode node2) {
        if (node2 == null) return true;
        else if (node1 == null) return false;
        else if (node1.val != node2.val) return false;
        else return recursive(node1.left, node2.left) && recursive(node1.right, node2.right);
    }
}

class Mirror {
    public void Mirror(TreeNode root) {
        if (root == null) return;
        Mirror(root.left);
        Mirror(root.right);
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
    }
}

class printMatrix {
    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return list;
        int m = matrix.length;
        int n = matrix[0].length;
        int top = 0, left = 0, right = n - 1, bottom = m - 1;
        int i = 0, j = 0;
        while (top <= bottom && left <= right) {

            j = left;
            while (j <= right && top <= bottom) list.add(matrix[top][j++]);
            top++;

            i = top;
            while (i <= bottom && left <= right) list.add(matrix[i++][right]);
            right--;

            j = right;
            while (j >= left && top <= bottom) list.add(matrix[bottom][j--]);
            bottom--;

            i = bottom;
            while (i >= top && left <= right) list.add(matrix[i--][left]);
            left++;

        }
        return list;
    }
}

class getMinStack {

    Stack<Integer> st1 = new Stack<Integer>();
    Stack<Integer> st2 = new Stack<Integer>();

    public void push(int node) {
        st1.push(node);
        if (st2.isEmpty() || node <= st2.peek()) st2.push(node);
    }

    public void pop() {
        if (st1.peek() == st2.peek()) st2.pop();
        st1.pop();
    }

    public int top() {
        return st1.peek();
    }

    public int min() {
        return st2.peek();
    }
}

class IsPopOrder {
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null || pushA.length != popA.length) return false;
        Stack<Integer> stack = new Stack<Integer>();
        int index = 0;
        for (int i = 0; i < pushA.length; i++) {
            stack.push(pushA[i]);
            while (stack.size() > 0 && stack.peek() == popA[index]) {
                stack.pop();
                index++;
            }
        }
        return stack.size() > 0 ? false : true;
    }
}

class VerifySequenceOfBST {
    public boolean VerifySequenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        if (sequence.length <= 2) return true;
        return recursive(sequence, 0, sequence.length - 1);
    }

    private boolean recursive(int[] seq, int start, int end) {
        if (end - start <= 1) {
            return true;
        } else {
            int root = seq[end];
            int split = end - 1;
            while (split > start && seq[split] > root) split--;
            for (int i = split - 1; i >= start; i--) if (seq[i] > root) return false;
            return recursive(seq, start, split) && recursive(seq, split + 1, end - 1);
        }
    }
}

class CloneRandomListNode {
    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) return null;
        Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode p = pHead;
        while (p != null) {
            map.put(p, new RandomListNode(p.label));
            p = p.next;
        }
        p = pHead;
        while (p != null) {
            if (p.next != null) map.get(p).next = map.get(p.next);
            if (p.random != null) map.get(p).random = map.get(p.random);
            p = p.next;
        }
        return map.get(pHead);
    }
}

class ConvertBSTtoDoubleLinkedArray {
    public TreeNode Convert(TreeNode root) {
        if (root == null) return null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = root, pre = null, head = null;
        boolean isFirst = true;
        while (stack.size() > 0 || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (isFirst) {
                head = cur;
                pre = head;
                isFirst = false;
            } else {
                cur.left = pre;
                pre.right = cur;
                pre = cur;
            }
            cur = cur.right;
        }
        return head;
    }
}

class Permutation {
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> list = new ArrayList<String>();
        if (str == null || str.length() == 0) return list;
        char[] arr = str.toCharArray();
        Arrays.sort(arr);
        boolean[] used = new boolean[arr.length];
        backtrack(list, arr, new StringBuilder(), used);
        return list;
    }

    private void backtrack(ArrayList<String> list, char[] arr, StringBuilder sb, boolean[] used) {
        String str = sb.toString();
        if (str.length() == arr.length) list.add(str);
        else {
            for (int i = 0; i < arr.length; i++) {
                if (used[i] == true || i > 0 && arr[i] == arr[i - 1] && used[i - 1] == true) continue;
                sb.append(arr[i]);
                used[i] = true;
                backtrack(list, arr, sb, used);
                sb.deleteCharAt(sb.length() - 1);
                used[i] = false;
            }
        }
    }
}

class MoreThanHalfNum {
    public int MoreThanHalfNum(int[] array) {
        if (array == null || array.length == 0) return 0;
        int cur = array[0], count = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] == cur) count++;
            else {
                count--;
                if (count == 0) {
                    cur = array[i];
                    count = 1;
                }
            }
        }
        count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == cur) count++;
        }
        if (count * 2 > array.length) return cur;
        return 0;
    }
}

class GetLeastNumbers {
    public ArrayList<Integer> GetLeastNumbers(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (input == null || input.length == 0 || k <= 0 || k > input.length) return list;
        //if(k>input.length) k=input.length;

        int[] heap = new int[k + 1];
        for (int i = 1; i <= k; i++) heap[i] = input[i - 1];
        for (int i = k / 2; i >= 1; i--) sink(heap, i, k);
        for (int i = k; i < input.length; i++) {
            if (input[i] < heap[1]) {
                heap[1] = input[i];
                sink(heap, 1, k);
            }
        }

        for (int i = 1; i < heap.length; i++) list.add(heap[i]);
        return list;
    }

    private void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    private void sink(int[] heap, int i, int len) {
        while (i * 2 <= len) {
            int child = i * 2;
            if (child + 1 <= len && heap[child + 1] > heap[child]) child++;
            if (heap[i] < heap[child]) {
                swap(heap, i, child);
                i = child;
            } else {
                break;
            }
        }
    }
}

class FindGreatestSumOfSubArray {
    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) return 0;
        int[] dp = new int[array.length];
        dp[0] = array[0];
        int max = dp[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = dp[i - 1] > 0 ? array[i] + dp[i - 1] : array[i];
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}

class NumberOf1Between1AndN {
    public int NumberOf1Between1AndN(int n) {
        int count = 0;
        while (n > 0) {
            String str = String.valueOf(n);
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '1')
                    count++;
            }
            n--;
        }
        return count;
    }
}

class PrintMinNumber {
    public String PrintMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return "";
        String[] strs = new String[numbers.length];
        for (int i = 0; i < strs.length; i++) strs[i] = numbers[i] + "";
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                String s1 = str1 + str2, s2 = str2 + str1;
                return s1.compareTo(s2);
            }
        });
        String result = "";
        for (String str : strs) result += str;
        return result;
    }
}

class GetUglyNumber {
    public int GetUglyNumber(int index) {
        if (index <= 0) return 0;
        int t2 = 0, t3 = 0, t5 = 0;
        int[] arr = new int[index];
        arr[0] = 1;
        for (int i = 1; i < index; i++) {
            arr[i] = Math.min(arr[t2] * 2, Math.min(arr[t3] * 3, arr[t5] * 5));
            if (arr[i] == arr[t2] * 2) t2++;
            if (arr[i] == arr[t3] * 3) t3++;
            if (arr[i] == arr[t5] * 5) t5++;
        }
        return arr[index - 1];
    }
}

class FindFirstCommonNode {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) return null;
        ListNode a = pHead1, b = pHead2;
        while (a != b) {
            a = a == null ? pHead2 : a.next;
            b = b == null ? pHead1 : b.next;
        }
        return a;
    }
}

class GetNumberOfK {
    public int GetNumberOfK(int[] array, int k) {
        if (array == null || array.length == 0) return 0;
        int leftIndex, rightIndex;

        // find the first k
        int lo = 0, hi = array.length - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (k <= array[mid]) hi = mid;
            else lo = mid + 1;
        }
        leftIndex = hi;

        // find the last k
        lo = 0;
        hi = array.length - 1;
        while (lo < hi) {
            int mid = (lo + hi + 1) / 2;
            if (k >= array[mid]) lo = mid;
            else hi = mid - 1;
        }
        rightIndex = lo;

        return array[leftIndex] == k ? rightIndex - leftIndex + 1 : 0;
    }
}

class IsBalanced {
    public boolean IsBalanced(TreeNode root) {
        return getDepth(root) == -1 ? false : true;
    }

    private int getDepth(TreeNode node) {
        if (node == null) return 0;

        int left = getDepth(node.left);
        if (left == -1) return -1;

        int right = getDepth(node.right);
        if (right == -1) return -1;

        return Math.abs(left - right) > 1 ? -1 : 1 + Math.max(left, right);
    }
}

class InversePairs {
    private int[] helper;

    public int InversePairs(int[] array) {
        if (array == null || array.length == 0) return 0;
        helper = new int[array.length];
        return recursiveMerge(array, 0, array.length - 1);
    }

    private int recursiveMerge(int[] array, int lo, int hi) {
        if (lo >= hi) return 0;
        int mid = lo + (hi - lo) / 2;
        recursiveMerge(array, lo, mid);
        recursiveMerge(array, mid + 1, hi);
        return merge(array, lo, mid, hi);
    }

    private int merge(int[] array, int lo, int mid, int hi) {
        for (int i = lo; i <= hi; i++) helper[i] = array[i];
        int i = mid, j = hi, count = 0;
        for (int k = hi; k >= lo; k--) {
            if (i < lo) array[k] = helper[j--];
            else if (j <= mid) array[k] = helper[i--];
            else if (helper[i] > helper[j]) {
                count += j - mid;
                array[k] = helper[i--];
            } else {
                array[k] = helper[j--];
            }
        }
        return count;
    }
}

class FindNumsAppearOnce {
    public void FindNumsAppearOnce(int[] array) {
        if (array == null || array.length == 0) return;

        int xor = array[0];
        for (int i = 1; i < array.length; i++) xor ^= array[i];

        int flag = 0;
        for (int i = 0; i < 32; i++) {
            if ((xor >> flag & 1) == 1) break;
            flag++;
        }

        int n1 = 0, n2 = 0;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] >> flag & 1) == 1)
                n1 ^= array[i];
            else
                n2 ^= array[i];
        }

        System.out.println(n1);
        System.out.println(n2);
    }
}

class FindContinuousSequence {
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        for (int len = 2; len <= sum; len++) {
            int s = (len - 1) * len / 2;
            if (s < sum) {
                int begin = (sum - s) / len;
                if ((begin * len + s) - sum == 0) {
                    add(list, begin, len);
                }
            } else {
                break;
            }
        }
        return list;
    }

    private void add(ArrayList<ArrayList<Integer>> list, int begin, int len) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i < len; i++) {
            arr.add(begin + i);
        }
        list.add(0, arr);
    }
}

class FindNumbersWithSum {
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int left = 0, right = array.length - 1;
        int min = Integer.MAX_VALUE;
        while (left < right) {
            int s = array[left] + array[right];
            int m = array[left] * array[right];
            if (s == sum && m < min) {
                list.clear();
                list.add(array[left]);
                list.add(array[right]);
                left++;
                right--;
                min = m;
            } else if (s < sum) {
                left++;
            } else {
                right--;
            }
        }
        return list;
    }
}

class LeftRotateString {
    public String LeftRotateString(String str, int n) {
        if (str.isEmpty() || n <= 0) return str;
        n = n % str.length();
        return str.substring(n) + str.substring(0, n);
    }
}

class isContinuous {
    public boolean isContinuous(int[] numbers) {
        if (numbers == null || numbers.length == 0) return false;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, zeroCount = 0;
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 0) {
                zeroCount++;
                continue;
            }
            min = Math.min(min, numbers[i]);
            max = Math.max(max, numbers[i]);
            if (set.contains(numbers[i])) return false;
            else set.add(numbers[i]);
        }
        if (zeroCount > 4) return false;
        if (max == 0) return true;
        for (int i = min; i <= max; i++) {
            if (!set.contains(i)) {
                if (zeroCount > 0) zeroCount--;
                else return false;
            }
        }
        return true;
    }
}

class Sum {
    public int Sum(int n) {
        int sum = n;
        boolean ans = (n > 0) && ((sum += Sum(n - 1)) > 0);
        return sum;
    }
}

class Add {
    public int Add(int num1, int num2) {
        while (num2 != 0) {
            int temp = num1 ^ num2;
            num2 = (num1 & num2) << 1;
            num1 = temp;
        }
        return num1;
    }
}

class StrToInt {
    public int StrToInt(String str) {
        if (str == null || str.trim().equals("")) return 0;
        int n = 0, sign = 1;
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (i == 0) {
                if (c == '+') continue;
                if (c == '-') {
                    sign = -1;
                    continue;
                }
            }
            if (!isNumber(c)) return 0;
            else {
                n = n * 10 + (c - '0');
            }
        }
        return n * sign;
    }

    private boolean isNumber(char c) {
        if ('0' <= c && c <= '9') return true;
        else return false;
    }
}

class FirstAppearingOnce {

    Map<Character, Integer> map = new HashMap<Character, Integer>();
    List<Character> list = new ArrayList<Character>();

    //Insert one char from stringstream
    public void Insert(char ch) {
        if (map.containsKey(ch)) map.put(ch, map.get(ch) + 1);
        else map.put(ch, 1);
        list.add(ch);
    }

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        for (char ch : list) {
            if (map.get(ch) == 1) {
                return ch;
            }
        }
        return '#';
    }
}

class EntryNodeOfLoop {
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null || pHead.next == null || pHead.next.next == null) return null;
        ListNode fast = pHead.next.next, slow = pHead.next;
        while (fast != slow) {
            if (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            } else
                return null;
        }
        fast = pHead;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}

class deleteDuplication {
    public ListNode deleteDuplication(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode tempHead = new ListNode(0);
        tempHead.next = head;
        ListNode p = tempHead, q = p.next;
        while (q != null && q.next != null) {
            if (q.next.val != q.val) {
                p = p.next;
                q = q.next;
            } else {
                while (q != null && q.next != null && q.next.val == q.val) q = q.next;
                q = q.next;
                p.next = q;
            }
        }
        return tempHead.next;
    }
}

class GetNext {
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null) return pNode;
        if (pNode.right == null) {
            while (pNode.next != null && pNode == pNode.next.right) pNode = pNode.next;
            return pNode.next;
        } else {
            pNode = pNode.right;
            while (pNode.left != null) pNode = pNode.left;
            return pNode;
        }
    }
}

class isSymmetrical {
    public boolean isSymmetrical(TreeNode root) {
        if (root == null) return true;
        return recursive(root.left, root.right);
    }

    private boolean recursive(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) return true;
        else if (node1 == null || node2 == null) return false;
        else if (node1.val != node2.val) return false;
        else return recursive(node1.left, node2.right) && recursive(node1.right, node2.left);
    }
}

class PrintTreeZigZag {
    public ArrayList<ArrayList<Integer>> Print(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        if (root == null) return list;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        ArrayList<Integer> layer;
        TreeNode cur;
        int traverse = 0;
        while (queue.size() > 0) {
            layer = new ArrayList<Integer>();
            int layerSize = queue.size();
            for (int i = 0; i < layerSize; i++) {
                cur = queue.poll();
                if (traverse == 0) layer.add(cur.val);
                else layer.add(0, cur.val);
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            traverse = 1 - traverse;
            list.add(layer);
        }
        return list;
    }
}

class SerializeTree {
    public String Serialize(TreeNode root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        sb.append(root.val + "!");
        TreeNode cur;
        while (queue.size() > 0) {
            cur = queue.poll();
            if (cur.left != null) {
                queue.offer(cur.left);
                sb.append(cur.left.val + "!");
            } else {
                sb.append("#!");
            }
            if (cur.right != null) {
                queue.offer(cur.right);
                sb.append(cur.right.val + "!");
            } else {
                sb.append("#!");
            }
        }
        return sb.toString();
    }

    public TreeNode Deserialize(String str) {
        if (str.isEmpty()) return null;
        String[] vals = str.split("!");
        TreeNode root = new TreeNode(Integer.valueOf(vals[0]));
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int index = 0;
        TreeNode cur, node;
        while (index < vals.length - 1) {
            cur = queue.poll();
            if (!vals[index + 1].equals("#")) {
                node = new TreeNode(Integer.valueOf(vals[++index]));
                cur.left = node;
                queue.offer(node);
            } else {
                index++;
            }
            if (!vals[index + 1].equals("#")) {
                node = new TreeNode(Integer.valueOf(vals[++index]));
                cur.right = node;
                queue.offer(node);
            } else {
                index++;
            }
        }
        return root;
    }
}

class KthNode {
    TreeNode KthNode(TreeNode root, int k) {
        if (root == null || k <= 0) return null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = root;
        while (cur != null || stack.size() > 0) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (--k == 0) return cur;
            cur = cur.right;
        }
        return null;
    }
}

class maxInWindows {
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (num == null || num.length == 0 || size <= 0 || num.length < size) return list;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        int startIndex = 0;
        for (int i = 0; i < num.length; i++) {
            while (queue.size() > 0 && num[i] >= num[queue.getLast()]) queue.removeLast();
            queue.add(i);
            if (i - queue.getFirst() + 1 > size) queue.removeFirst();
            if (i >= size - 1) {
                list.add(num[queue.getFirst()]);
                startIndex++;
            }
        }
        return list;
    }
}

class hasPath {
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (rows == 0 || cols == 0 || str.length == 0) return false;

        char[][] m = new char[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = matrix[index++];
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] visited = new boolean[rows][cols];
                if (search(m, i, j, str, 0, visited)) return true;
            }
        }

        return false;
    }

    private boolean search(char[][] m, int i, int j, char[] str, int index, boolean[][] visited) {
        if (i < 0 || i >= m.length || j < 0 || j >= m[0].length || visited[i][j] == true || m[i][j] != str[index])
            return false;
        visited[i][j] = true;
        index++;
        if (index > str.length - 1) return true;
        return search(m, i - 1, j, str, index, visited)
                || search(m, i + 1, j, str, index, visited)
                || search(m, i, j - 1, str, index, visited)
                || search(m, i, j + 1, str, index, visited);
    }
}

class movingCount {
    private int count = 0;

    public int movingCount(int threshold, int rows, int cols) {
        boolean[][] visited = new boolean[rows][cols];
        search(visited, 0, 0, threshold);
        return count;
    }

    private void search(boolean[][] visited, int i, int j, int k) {
        if (i < 0 || i > visited.length - 1 || j < 0 || j > visited[0].length - 1 || visited[i][j] || sum(i, j) > k)
            return;
        count++;
        visited[i][j] = true;
        search(visited, i - 1, j, k);
        search(visited, i + 1, j, k);
        search(visited, i, j - 1, k);
        search(visited, i, j + 1, k);
    }

    private int sum(int i, int j) {
        int sum = 0;
        while (i != 0) {
            sum += i % 10;
            i /= 10;
        }
        while (j != 0) {
            sum += j % 10;
            j /= 10;
        }
        return sum;
    }
}
