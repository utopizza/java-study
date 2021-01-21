package coding;

import java.util.*;

class Interval {
    int start;
    int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e) {
        start = s;
        end = e;
    }
}

class lt_3 {
    public int lengthOfLongestSubstring(String s) {
        int i = 0, j = 0, max = 0;
        Set<Character> set = new HashSet<>();
        while (j < s.length()) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                max = Math.max(max, set.size());
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return max;
    }
}

class lt_5 {
    private String str = "";

    public String longestPalindrome(String s) {
        for (int i = 0; i < s.length(); i++) {
            extend(s, i, i);
            extend(s, i, i + 1);
        }
        return str;
    }

    private void extend(String s, int i, int j) {
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
        }
        if (--j - ++i + 1 > str.length())
            str = s.substring(i, j + 1);
    }
}

class lt_6 {
    public String convert(String s, int numRows) {
        if (s.isEmpty() || numRows <= 1) return s;
        if (numRows > s.length()) numRows = s.length();
        StringBuilder[] sbs = new StringBuilder[numRows];
        for (int i = 0; i < sbs.length; i++) sbs[i] = new StringBuilder();
        int j = 0;
        boolean reverse = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!reverse) {
                sbs[j++].append(c);
                if (j > sbs.length - 1) {
                    j = sbs.length - 2;
                    reverse = true;
                }
            } else {
                sbs[j--].append(c);
                if (j < 0) {
                    j = 1;
                    reverse = false;
                }
            }
        }
        String result = "";
        for (StringBuilder sb : sbs) result += sb.toString();
        return result;
    }
}

class lt_8 {
    public int myAtoi(String str) {
        if (str.trim().isEmpty()) return 0;
        char[] arr = str.toCharArray();
        int i = 0, signal = 1;

        // skip leading space
        while (arr[i] == ' ') {
            i++;
        }

        // get signal
        if (arr[i] == '+') {
            i++;
        } else if (arr[i] == '-') {
            signal = -1;
            i++;
        }

        // get number
        int number = 0;
        while (i < arr.length && arr[i] >= '0' && arr[i] <= '9') {
            int digit = arr[i] - '0';
            if (signal > 0 && number > (Integer.MAX_VALUE - digit) / 10) return Integer.MAX_VALUE;
            if (signal < 0 && number * signal < (Integer.MIN_VALUE + digit) / 10) return Integer.MIN_VALUE;
            number = number * 10 + digit;
            i++;
        }

        return number * signal;
    }
}

class lt_11 {
    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1, max = 0;
        while (i < j) {
            if (height[i] < height[j]) {
                max = Math.max(max, height[i] * (j - i));
                i++;
            } else {
                max = Math.max(max, height[j] * (j - i));
                j--;
            }
        }
        return max;
    }
}

class lt_17 {
    public List<String> letterCombinations(String digits) {
        String[] map = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        LinkedList<String> list = new LinkedList<>();
        if (digits.isEmpty()) return list;
        list.add("");
        for (char c : digits.toCharArray()) {
            String key = map[c - '0'];
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String str = list.removeFirst();
                for (char x : key.toCharArray()) {
                    list.add(str + x);
                }
            }
        }
        return list;
    }
}

class lt_19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) return head;
        ListNode slow = head, fast = head;

        // fast go n step
        while (fast.next != null && n > 0) {
            fast = fast.next;
            n--;
        }
        if (n > 1) return head;
        if (n == 1) return head.next;

        // slow move with fast to the end
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        if (slow.next != null) slow.next = slow.next.next;

        return head;
    }
}

class lt_22 {
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        if (n <= 0) return list;
        backtrack(list, "", 0, 0, n);
        return list;
    }

    private void backtrack(List<String> list, String str, int open, int close, int n) {
        if (open == n && close == n) list.add(str);
        else {
            if (open < n) backtrack(list, str + "(", open + 1, close, n);
            if (close < open) backtrack(list, str + ")", open, close + 1, n);
        }
    }
}

class lt_29 {
    public int divide(int dividend, int divisor) {
        if (divisor == 0) return 0;
        int signal = 1;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) signal = -1;
        long a = Math.abs((long) dividend), b = Math.abs((long) divisor);
        long result = calculate(a, b);
        int res = 0;
        if (signal == 1 && result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (signal == -1 && result * signal < Integer.MIN_VALUE) return Integer.MIN_VALUE;
        res = (int) result;
        return res * signal;
    }

    private long calculate(long a, long b) {
        if (a < b) return 0;
        long result = 1;
        long c = b;
        while (c + c <= a) {
            c = c + c;
            result = result + result;
        }
        return result + calculate(a - c, b);
    }
}

class lt_31 {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return;

        // find the left bound
        int i = nums.length - 1;
        while (i > 0 && nums[i] <= nums[i - 1]) i--;
        i--;

        // adjust
        if (i < 0) {
            // the nums is asc sorted
            reverse(nums, 0, nums.length - 1);
        } else {
            // find the right bound
            int j = i + 1;
            while (j < nums.length && nums[j] > nums[i]) j++;

            // swap with right bound
            swap(nums, i, --j);

            // reverse left bound to right
            int lo = i + 1, hi = nums.length - 1;
            reverse(nums, lo, hi);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int begin, int end) {
        while (begin < end) {
            swap(nums, begin++, end--);
        }
    }
}

class lt_33 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[lo] <= nums[mid]) { // nums[lo...mid] is sorted
                if (nums[lo] <= target && target <= nums[mid]) hi = mid;
                else lo = mid + 1;
            } else { // nums[mid...hi] is sorted
                if (nums[mid] <= target && target <= nums[hi]) lo = mid;
                else hi = mid - 1;
            }
        }
        return nums[lo] == target ? lo : -1;
    }
}

class lt_34 {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) return new int[]{-1, -1};
        return new int[]{findLeftBound(nums, target), findRightBound(nums, target)};
    }

    private int findLeftBound(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (target <= nums[mid]) hi = mid;
            else lo = mid + 1;
        }
        return nums[hi] == target ? hi : -1;
    }

    private int findRightBound(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (target >= nums[mid]) lo = mid;
            else hi = mid - 1;
        }
        return nums[lo] == target ? lo : -1;
    }
}

class lt_36 {
    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length != 9 || board[0].length != 9) return false;
        Set[] rowSets = new HashSet[9], colSets = new HashSet[9], blockSets = new HashSet[9];
        for (int i = 0; i < 9; i++) rowSets[i] = new HashSet<Integer>();
        for (int j = 0; j < 9; j++) colSets[j] = new HashSet<Integer>();
        for (int k = 0; k < 9; k++) blockSets[k] = new HashSet<Integer>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int num = board[i][j] - '0';
                if (!rowSets[i].add(num)) return false;
                if (!colSets[j].add(num)) return false;
                if (!blockSets[i / 3 * 3 + j / 3].add(num)) return false;
            }
        }
        return true;
    }
}

class lt_40 {
    public List<List<Integer>> combinationSum2(int[] nums, int target) {
        List<List<Integer>> soluList = new ArrayList<>();
        if (nums == null || nums.length == 0) return soluList;
        Arrays.sort(nums);
        backtrack(soluList, new ArrayList<Integer>(), nums, target, 0, 0);
        return soluList;
    }

    private void backtrack(List<List<Integer>> soluList, List<Integer> solu, int[] nums, int target, int index, int tempSum) {
        if (tempSum == target) soluList.add(new ArrayList(solu));
        else if (tempSum > target) return;
        else {
            for (int i = index; i <= nums.length - 1; i++) {
                if (i > index && nums[i] == nums[i - 1]) continue;
                solu.add(nums[i]);
                backtrack(soluList, solu, nums, target, i + 1, tempSum + nums[i]);
                solu.remove(solu.size() - 1);
            }
        }
    }
}

class lt_49 {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        if (strs == null || strs.length == 0) return result;

        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] charArr = str.toCharArray();
            Arrays.sort(charArr);
            String key = String.valueOf(charArr);
            if (map.containsKey(key)) map.get(key).add(str);
            else {
                List<String> list = new ArrayList<String>();
                list.add(str);
                map.put(key, list);
            }
        }

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            result.add((List<String>) entry.getValue());
        }

        return result;
    }
}

class lt_50 {
    public double myPow(double x, int n) {
        if (n == Integer.MIN_VALUE && x > 1) return 0;
        if (x == 0) return 0;
        if (n == 0) return 1;
        if (n == 1) return x;
        if (n < 0) {
            x = 1 / x;
            n = 0 - n;
        }
        return (n & 1) == 0 ? myPow(x * x, n / 2) : x * myPow(x * x, n / 2);
    }
}

class lt_54 {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return list;
        int row = matrix.length;
        int col = matrix[0].length;
        int top = 0, bottom = row - 1, left = 0, right = col - 1;
        int i = 0, j = 0;
        while (left <= right && top <= bottom) {
            //top: left to right
            for (j = left; j <= right; j++) list.add(matrix[top][j]);
            top++;

            //right: top to bottom
            for (i = top; i <= bottom; i++) list.add(matrix[i][right]);
            right--;

            //bottom: right to left
            if (top <= bottom)
                for (j = right; j >= left; j--)
                    list.add(matrix[bottom][j]);
            bottom--;

            //left: bottom to top
            if (left <= right)
                for (i = bottom; i >= top; i--)
                    list.add(matrix[i][left]);
            left++;
        }
        return list;
    }
}

class lt_55 {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        int farthestIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (farthestIndex < i) return false;
            farthestIndex = Math.max(farthestIndex, nums[i] + i);
        }
        return true;
    }
}

class lt_56 {
    public List<Interval> merge(List<Interval> intervals) {
        LinkedList<Interval> result = new LinkedList<>();
        if (intervals == null) return result;
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval e1, Interval e2) {
                return e1.start - e2.start;
            }
        });
        for (Interval interval : intervals) {
            int size = result.size();
            if (size == 0 || interval.start > result.getLast().end) result.add(interval);
            else result.getLast().end = Math.max(result.getLast().end, interval.end);
        }
        return result;
    }
}

class lt_74 {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int m = matrix.length, n = matrix[0].length;
        int lo = 0, hi = m * n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (matrix[mid / n][mid % n] == target)
                return true;
            else if (matrix[mid / n][mid % n] > target)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return false;
    }
}

class lt_75 {
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int i = 0, j = nums.length - 1, k = 0;
        while (k <= j) {
            if (nums[k] == 0) swap(nums, i++, k++);
            else if (nums[k] == 2) swap(nums, j--, k);
            else k++;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}

class lt_77 {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> soluList = new ArrayList<>();
        if (n <= 0) return soluList;
        k = Math.min(n, k);

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = i + 1;

        backtrack(soluList, new ArrayList<Integer>(), nums, k, 0, 0);
        return soluList;
    }

    private void backtrack(List<List<Integer>> soluList, List<Integer> solu, int[] nums, int k, int tempK, int index) {
        if (tempK == k) soluList.add(new ArrayList<>(solu));
        else if (index > nums.length - 1) return;
        else {
            for (int i = index; i < nums.length; i++) {
                solu.add(nums[i]);
                backtrack(soluList, solu, nums, k, tempK + 1, i + 1);
                solu.remove(solu.size() - 1);
            }
        }
    }
}

class lt_79 {
    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0 || board[0].length == 0 || word.isEmpty()) return false;
        int row = board.length, col = board[0].length;
        boolean[][] used = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (search(board, word, i, j, 0, used)) return true;
            }
        }
        return false;
    }

    private boolean search(char[][] board, String word, int i, int j, int index, boolean[][] used) {
        if (index == word.length()) return true;
        if (i < 0 || i > board.length - 1 || j < 0 || j > board[0].length - 1 || board[i][j] != word.charAt(index) || used[i][j])
            return false;
        used[i][j] = true;
        boolean result = search(board, word, i - 1, j, index + 1, used)
                || search(board, word, i + 1, j, index + 1, used)
                || search(board, word, i, j - 1, index + 1, used)
                || search(board, word, i, j + 1, index + 1, used);
        used[i][j] = false;
        return result;
    }
}

class lt_81 {
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return true;
            if (nums[mid] < nums[hi] || nums[mid] < nums[lo]) {
                if (target > nums[mid] && target <= nums[hi]) lo = mid + 1;
                else hi = mid - 1;
            } else if (nums[mid] > nums[lo] || nums[mid] > nums[hi]) {
                if (target < nums[mid] && target >= nums[lo]) hi = mid - 1;
                else lo = mid + 1;
            } else {
                hi--;
            }
        }
        return false;
    }
}

class lt_120 {
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle == null || triangle.size() == 0) return 0;
        int len = triangle.size();
        int[][] dp = new int[len][len];
        dp[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < len; i++) {
            int col = triangle.get(i).size();
            for (int j = 0; j < col; j++) {
                if (j == 0) dp[i][j] = dp[i - 1][j];
                else if (j == col - 1) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]);
                dp[i][j] += triangle.get(i).get(j);
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) min = Math.min(min, dp[len - 1][i]);
        return min;
    }
}

class lt_127 {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int step = 1;
        while (queue.size() > 0) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String str = queue.poll();
                if (str.equals(endWord)) return step;
                for (int j = 0; j < str.length(); j++) {
                    char[] chars = str.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[j] = c;
                        String temp = new String(chars);
                        if (wordSet.contains(temp)) {
                            queue.offer(temp);
                            wordSet.remove(temp);
                        }
                    }
                }
            }
            step++;
        }
        return 0;
    }
}

class lt_165 {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int minLen = Math.min(v1.length, v2.length);
        int maxLen = Math.max(v1.length, v2.length);
        for (int i = 0; i < maxLen; i++) {
            if (i < minLen) {
                if (Integer.parseInt(v1[i]) > Integer.parseInt(v2[i])) return 1;
                else if (Integer.parseInt(v1[i]) < Integer.parseInt(v2[i])) return -1;
            } else {
                if (i < v1.length && Integer.parseInt(v1[i]) > 0) return 1;
                else if (i < v2.length && Integer.parseInt(v2[i]) > 0) return -1;
            }
        }
        return 0;
    }
}

class lt_209 {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int sum = 0, i = 0, min = Integer.MAX_VALUE;
        for (int j = 0; j < nums.length; j++) {
            sum += nums[j];
            while (sum >= s) {
                min = Math.min(min, j - i + 1);
                sum -= nums[i++];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}

class lt_220 {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (k <= 0 || t < 0) return false;
        HashMap<Long, Long> map = new HashMap<>();
        long div = (long) t + 1;
        for (int i = 0; i < nums.length; i++) {
            long num = (long) nums[i];
            long bucket = num / div;
            if (num < 0) bucket--;//?
            if (map.containsKey(bucket)
                    || map.containsKey(bucket + 1) && map.get(bucket + 1) <= num + t
                    || map.containsKey(bucket - 1) && map.get(bucket - 1) >= num - t)
                return true;
            if (i >= k) map.remove(((long) nums[i - k]) / div);
            map.put(bucket, num);
        }
        return false;
    }
}

class lt_241 {
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> ret = new ArrayList<>();
        if (input.isEmpty()) return ret;
        boolean containsOperator = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '+' || c == '*' || c == '-') {
                containsOperator = true;
                String part1 = input.substring(0, i);
                String part2 = input.substring(i + 1);
                List<Integer> ret1 = diffWaysToCompute(part1);
                List<Integer> ret2 = diffWaysToCompute(part2);
                for (int left : ret1) {
                    for (int right : ret2) {
                        if (c == '+') ret.add(left + right);
                        else if (c == '-') ret.add(left - right);
                        else ret.add(left * right);
                    }
                }
            }
        }
        if (!containsOperator) ret.add(Integer.parseInt(input));
        return ret;
    }
}

class lt_260 {
    public int[] singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) res ^= num;

        int move = 0;
        for (; move < 32; move++) {
            if ((res >> move & 1) == 1) break;
        }

        int[] ret = new int[]{0, 0};
        for (int num : nums) {
            if ((num >> move & 1) == 1) ret[0] ^= num;
            else ret[1] ^= num;
        }

        return ret;
    }
}

class lt_264 {
    public int nthUglyNumber(int index) {
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

class lt_274 {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) return 0;
        Arrays.sort(citations);
        int h = 0;
        for (int i = 0; i < citations.length; i++) {
            if (i + 1 <= citations[citations.length - 1 - i]) h = i + 1;
            else break;
        }
        return h;
    }
}

class lt_279 {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= n; ++i) {
            int min = Integer.MAX_VALUE;
            int j = 1;
            while (i - j * j >= 0) {
                min = Math.min(min, dp[i - j * j] + 1);
                ++j;
            }
            dp[i] = min;
        }
        return dp[n];
    }
}

class lt_289 {
    public void gameOfLife(int[][] board) {
        if (board.length == 0 || board[0].length == 0) return;
        int m = board.length, n = board[0].length;
        boolean[][] record = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (getState(board, i, j)) record[i][j] = true;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (record[i][j]) board[i][j] = 1;
                else board[i][j] = 0;
            }
        }
    }

    private boolean getState(int[][] board, int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (0 <= i && i < board.length && 0 <= j && j < board[0].length) count += board[i][j];
            }
        }
        count -= board[row][col];
        if (board[row][col] == 1) {
            if (2 <= count && count <= 3) return true;
            else return false;
        } else {
            if (count == 3) return true;
            else return false;
        }
    }
}

class lt_299 {
    public String getHint(String secret, String guess) {
        if (secret.isEmpty() || guess.isEmpty()) return "0A0B";

        int bulls = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i))
                bulls++;
        }

        int cows = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < secret.length(); i++) {
            char c = secret.charAt(i);
            if (!map.containsKey(c)) map.put(c, 0);
            map.put(c, map.get(c) + 1);
        }
        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            if (map.containsKey(c) && map.get(c) > 0) {
                cows++;
                map.put(c, map.get(c) - 1);
            }
        }
        cows -= bulls;

        return bulls + "A" + cows + "B";
    }
}

class lt_310 {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return Arrays.asList(0);
        Set<Integer>[] adj = new Set[n];
        for (int i = 0; i < adj.length; i++) adj[i] = new HashSet<>();
        for (int i = 0; i < edges.length; i++) {
            int node1 = edges[i][0];
            int node2 = edges[i][1];
            adj[node1].add(node2);
            adj[node2].add(node1);
        }

        LinkedList<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < adj.length; i++)
            if (adj[i].size() == 1)
                leaves.add(i);

        while (n > 2) {
            int size = leaves.size();
            n -= size;
            for (int i = 0; i < size; i++) {
                int leave = leaves.poll();
                int father = adj[leave].iterator().next();
                adj[leave].remove(father);
                adj[father].remove(leave);
                if (adj[father].size() == 1) leaves.add(father);
            }
        }

        return leaves;
    }
}

class lt_318 {
    public int maxProduct(String[] words) {
        int max = 0;
        Set<Character>[] sets = new HashSet[words.length];
        for (int i = 0; i < sets.length; i++) {
            sets[i] = new HashSet<>();
            for (char c : words[i].toCharArray()) sets[i].add(c);
        }
        for (int i = 0; i < sets.length; i++) {
            for (int j = i + 1; j < sets.length; j++) {
                if (!shared(sets[i], sets[j])) {
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }
        return max;
    }

    private boolean shared(Set<Character> set1, Set<Character> set2) {
        for (Character c : set1) if (set2.contains(c)) return true;
        for (Character c : set2) if (set1.contains(c)) return true;
        return false;
    }
}

class lt_322 {
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount <= 0) return 0;
        Arrays.sort(coins);
        int[][] dp = new int[coins.length][amount + 1];
        for (int j = 0; j < amount + 1; j++)
            dp[0][j] = j % coins[0] == 0 ? j / coins[0] : Integer.MAX_VALUE;
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j < amount + 1; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 0; coins[i] * k <= j; k++) {
                    if (dp[i - 1][j - coins[i] * k] != Integer.MAX_VALUE) {
                        min = Math.min(min, dp[i - 1][j - coins[i] * k] + k);
                    }
                }
                dp[i][j] = min;
            }
        }
        return dp[coins.length - 1][amount] == Integer.MAX_VALUE ? -1 : dp[coins.length - 1][amount];
    }
}

class lt_331 {
    public boolean isValidSerialization(String preorder) {
        String[] nodes = preorder.split(",");
        int diff = 1;
        for (String node : nodes) {
            if (--diff < 0) return false;
            if (!node.equals("#")) diff += 2;
        }
        return diff == 0;
    }
}

class lt_332 {
    private LinkedList<String> path;
    private Map<String, PriorityQueue<String>> map;

    public List<String> findItinerary(String[][] tickets) {
        path = new LinkedList<>();
        map = new HashMap<>();
        if (tickets == null || tickets.length == 0 || tickets[0].length < 2) return path;
        for (int i = 0; i < tickets.length; i++) {
            map.putIfAbsent(tickets[i][0], new PriorityQueue<>());
            map.get(tickets[i][0]).add(tickets[i][1]);
        }
        dfs("JFK");
        return path;
    }

    private void dfs(String source) {
        PriorityQueue<String> arrivals = map.get(source);
        while (arrivals != null && !arrivals.isEmpty()) dfs(arrivals.poll());
        path.addFirst(source);
    }
}

class lt_347 {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (!map.containsKey(n)) map.put(n, 0);
            map.put(n, map.get(n) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) heap.add(entry);

        List<Integer> ret = new ArrayList<>();
        while (ret.size() < k) ret.add(heap.poll().getKey());

        return ret;
    }
}

class lt_355 {

    private class Tweet {
        int userId;
        int tweetId;

        Tweet(int userId, int tweetId) {
            this.userId = userId;
            this.tweetId = tweetId;
        }
    }

    private LinkedList<Tweet> tweets;
    private HashMap<Integer, HashSet<Integer>> followees;

    public lt_355() {
        tweets = new LinkedList<>();
        followees = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        tweets.addFirst(new Tweet(userId, tweetId));
    }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> target = new HashSet<>();
        target.add(userId);
        if (followees.containsKey(userId)) target.addAll(followees.get(userId));
        List<Integer> ret = new LinkedList<>();
        int count = 10;
        for (Tweet tweet : tweets) {
            if (count == 0) break;
            if (target.contains(tweet.userId)) {
                ret.add(tweet.tweetId);
                count--;
            }
        }
        return ret;
    }

    public void follow(int followerId, int followeeId) {
        if (!followees.containsKey(followerId)) followees.put(followerId, new HashSet<>());
        followees.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followees.containsKey(followerId)) {
            followees.get(followerId).remove(followeeId);
        }
    }
}

class lt_368 {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if (nums == null || nums.length == 0) return new ArrayList<>();
        int[] len = new int[nums.length];
        int[] pre = new int[nums.length];
        int maxLen = 0, index = -1;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            len[i] = 1;
            pre[i] = -1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] % nums[j] == 0) {
                    if (len[j] + 1 > len[i]) {
                        len[i] = len[j] + 1;
                        pre[i] = j;
                    }
                }
            }
            if (len[i] > maxLen) {
                maxLen = len[i];
                index = i;
            }
        }
        List<Integer> res = new ArrayList<>();
        while (index != -1) {
            res.add(0, nums[index]);
            index = pre[index];
        }
        return res;
    }
}

class lt_372 {
    public int superPow(int a, int[] b) {
        int ret = 1;
        for (int i = 0; i < b.length; i++) {
            ret = (powMod(ret, 10, 1337) * powMod(a, b[i], 1337)) % 1337;
        }
        return ret;
    }

    private int powMod(int x, int y, int k) {
        x %= k;
        int pow = 1;
        for (int i = 0; i < y; i++) pow = (pow * x) % k;
        return pow;
    }
}

public class LeetCode {
    public static void main(String[] args) {
    }
}