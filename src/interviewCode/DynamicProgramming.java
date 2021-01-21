package interviewCode;

import java.util.Arrays;
import java.util.HashMap;

public class DynamicProgramming {
    public static void main(String[] args) {
        //int[][] m = new int[][]{{1, 3, 5, 9}, {8, 1, 3, 4}, {5, 0, 6, 1}, {8, 8, 4, 0}};
        //System.out.println(new MinPathSum().minPathSum(m));
        //int[] coins = new int[]{5, 25, 10, 1};
        //System.out.println(new CoinsExchangeWays().coinsExchangeWays(coins, 15));
        //int[] s = new int[]{2, 1, 5, 3, 6, 4, 8, 9, 7};
        //System.out.println(new LIS().LIS(s));
        //int[] A = new int[]{1, 3, 4, 2, 5, 7, 3};
        //int[] B = new int[]{1, 3, 4, 2, 3};
        //System.out.println(new LCSA().LCSA(A, B));
        //String str1 = "abc", str2 = "adc";
        //System.out.println(new MinEditCost().minEditCost(str1, str2, 5, 4, 30));
        //String str1 = "ab", str2 = "13", aim = "a1b2";
        //System.out.println(new StringCross().isCross(str1, str2, aim));
        //int[][] map = new int[][]{{-2, -3, 3}, {-5, -10, 1}, {0, 30, -5}};
        //System.out.println(new MinHP().minHP(map));
        System.out.println(new ChangeWays().changeWays("1111"));
    }
}

class MinPathSum {
    public int minPathSum(int[][] m) {
        if (m.length == 0 || m[0].length == 0) return 0;
        int row = m.length, col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        for (int i = 1; i < row; i++) dp[i][0] = dp[i - 1][0] + m[i][0];
        for (int j = 1; j < col; j++) dp[0][j] = dp[0][j - 1] + m[0][j];
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }
}

class MinCoins {

    // each coin can be used infinite times
    public int minCoins(int[] coins, int target) {
        if (coins.length == 0 || target <= 0) return 0;
        int[] dp = new int[target + 1];
        int max = Integer.MAX_VALUE;
        Arrays.sort(coins); // let "coins[j]<=i" to skip some useless solutions
        for (int i = 1; i <= target; i++) {
            dp[i] = max;
            for (int j = 0; j < coins.length && coins[j] <= i; j++) {
                if (dp[i - coins[j]] != max) dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
            }
        }
        return dp[target] == max ? 0 : dp[target];
    }

    // each coin can be used only once
    public int minCoins2(int[] coins, int target) {
        if (coins.length == 0 || target <= 0) return 0;
        int[][] dp = new int[coins.length][target + 1];
        int max = Integer.MAX_VALUE;

        for (int j = 1; j <= target; j++) dp[0][j] = max;
        if (target >= coins[0]) dp[0][coins[0]] = 1;

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j <= target; j++) {
                if (j - coins[i] >= 0)
                    if (dp[i - 1][j - coins[i]] == max) dp[i][j] = dp[i - 1][j];
                    else dp[i][j] = Math.min(dp[i - 1][j - coins[i]] + 1, dp[i - 1][j]);
                else
                    dp[i][j] = max;
            }
        }
        return dp[coins.length - 1][target] == max ? 0 : dp[coins.length - 1][target];
    }

}

class CoinsExchangeWays {

    // each coin can be used infinite times
    public int coinsExchangeWays(int[] coins, int target) {
        if (coins.length == 0 || target <= 0) return 0;
        int[][] dp = new int[coins.length][target + 1];
        for (int i = 0; i < coins.length; i++) dp[i][0] = 1;
        for (int j = 0; j * coins[0] <= target; j++) dp[0][j * coins[0]] = 1;
        for (int j = 1; j <= target; j++) {
            for (int i = 1; i < coins.length; i++) {
                int sum = 0;
                for (int k = 0; k * coins[i] <= j; k++) {
                    sum += dp[i - 1][j - k * coins[i]];
                }
                dp[i][j] = sum;
            }
        }
        return dp[coins.length - 1][target];
    }

}

class LIS {
    public int LIS(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int[] dp = new int[arr.length];
        int maxLen = 0;
        for (int i = 0; i < arr.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}

class LCS {
    public int LCS(int[] A, int[] B) {
        if (A.length == 0 || B.length == 0) return 0;
        int[][] dp = new int[A.length][B.length];
        dp[0][0] = A[0] == B[0] ? 1 : 0;
        for (int i = 1; i < A.length; i++) dp[i][0] = dp[0][0];
        for (int j = 1; j < B.length; j++) dp[0][j] = dp[0][0];
        for (int i = 1; i < A.length; i++) {
            for (int j = 1; j < B.length; j++) {
                if (A[i] == B[j]) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[A.length - 1][B.length - 1];
    }
}

class LCSA {
    public int LCSA(int[] A, int[] B) {
        if (A.length == 0 || B.length == 0) return 0;
        int[][] dp = new int[A.length][B.length];
        dp[0][0] = A[0] == B[0] ? 1 : 0;
        for (int i = 1; i < A.length; i++) dp[i][0] = dp[0][0];
        for (int j = 1; j < B.length; j++) dp[0][j] = dp[0][0];
        int max = 0;
        for (int i = 1; i < A.length; i++) {
            for (int j = 1; j < B.length; j++) {
                if (A[i] == B[j]) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = 0;
                max = Math.max(max, dp[i][j]);
            }
        }
        return max;
    }
}

class MinEditCost {
    public int minEditCost(String str1, String str2, int insertCost, int deleteCost, int replaceCost) {
        if (str1 == null || str2 == null) return 0;
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= str1.length(); i++) dp[i][0] = deleteCost * i;
        for (int j = 1; j <= str2.length(); j++) dp[0][j] = insertCost * j;
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                int insertOneCost = dp[i][j - 1] + insertCost;
                int deleteOneCost = dp[i - 1][j] + deleteCost;
                int replaceOneCost = str1.charAt(i - 1) == str2.charAt(j - 1) ? dp[i - 1][j - 1] : dp[i - 1][j - 1] + replaceCost;
                dp[i][j] = Math.min(insertOneCost, Math.min(deleteOneCost, replaceOneCost));
            }
        }
        return dp[str1.length()][str2.length()];
    }
}

class StringCross {
    public boolean isCross(String str1, String str2, String aim) {
        if (str1 == null || str2 == null || aim == null || aim.length() != str1.length() + str2.length()) return false;
        boolean[][] dp = new boolean[str1.length() + 1][str2.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= str1.length(); i++) {
            if (str1.charAt(i - 1) == aim.charAt(i - 1)) dp[i][0] = true;
            else break;
        }
        for (int j = 1; j <= str2.length(); j++) {
            if (str2.charAt(j - 1) == aim.charAt(j - 1)) dp[0][j] = true;
            else break;
        }
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                boolean a = dp[i - 1][j] && str1.charAt(i - 1) == aim.charAt(i + j - 1);
                boolean b = dp[i][j - 1] && str2.charAt(j - 1) == aim.charAt(i + j - 1);
                dp[i][j] = a || b;
            }
        }
        return dp[str1.length()][str2.length()];
    }
}

class MinHP {
    public int minHP(int[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) return 0;
        int row = map.length - 1, col = map[0].length - 1;
        int[][] dp = new int[row + 1][col + 1];
        dp[row][col] = Math.max(1 - map[row][col], 1);
        for (int i = row - 1; i >= 0; i--) dp[i][col] = Math.max(dp[i + 1][col] - map[i][col], 1);
        for (int j = col - 1; j >= 0; j--) dp[row][j] = Math.max(dp[row][j + 1] - map[row][j], 1);
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                int toRight = Math.max(dp[i][j + 1] - map[i][j], 1);
                int toDown = Math.max(dp[i + 1][j] - map[i][j], 1);
                dp[i][j] = Math.min(toRight, toDown);
            }
        }
        return dp[0][0];
    }
}

class ChangeWays {
    public int changeWays(String num) {
        if (num.isEmpty() || num.startsWith("0")) return 0;
        if (num.length() == 1) return 1;
        char[] chars = num.toCharArray();
        int[] nums = new int[num.length()];
        for (int i = 0; i < nums.length; i++)
            nums[i] = chars[i] - '0';

        int[] dp = new int[num.length()];
        dp[0] = 1;
        dp[1] = (nums[0] * 10 + nums[1]) <= 26 ? 2 : 1;
        for (int i = 2; i < dp.length; i++) {
            int sum = nums[i - 1] * 10 + nums[i];
            if (sum < 1) return 0;
            dp[i] = dp[i - 1];
            if (sum < 27) dp[i] += dp[i - 2];
        }

        return dp[dp.length - 1];
    }
}

class Nim {
    public int win(int[] scores) {
        if (scores == null || scores.length == 0) return 0;
        int[][] f = new int[scores.length][scores.length];
        int[][] s = new int[scores.length][scores.length];
        for (int j = 0; j < scores.length; j++) {
            f[j][j] = scores[j];
            for (int i = j - 1; i >= 0; i--) {
                f[i][j] = Math.max(scores[i] + s[i + 1][j], scores[j] + s[i][j - 1]);
                s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
            }
        }
        return Math.max(f[0][scores.length - 1], s[0][scores.length - 1]);
    }
}

class Jump {
    public int jump(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int jump = 0, cur = 0, next = 0;
        for (int i = 0; i < arr.length; i++) {
            if (cur < i) {
                jump++;
                cur = next;
            }
            next = Math.max(next, i + arr[i]);
        }
        return jump;
    }
}

class LongestConsecutive {
    public int longestConsecutive(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int max = 1;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], 1);
                if (map.containsKey(arr[i] - 1)) max = Math.max(max, merge(map, arr[i] - 1, arr[i]));
                if (map.containsKey(arr[i] + 1)) max = Math.max(max, merge(map, arr[i], arr[i] + 1));
            }
        }
        return max;
    }

    private int merge(HashMap<Integer, Integer> map, int less, int more) {
        int left = less - map.get(less) + 1;
        int right = more + map.get(more) - 1;
        int len = right - left + 1;
        map.put(left, len);
        map.put(right, len);
        return len;
    }
}

class NQueens {
    public int putQueens(int n) {
        if (n < 1) return 0;
        int[] record = new int[n];
        return backtrack(0, record, n);
    }

    private int backtrack(int i, int[] record, int n) {
        if (i == n) return 1;
        int res = 0;
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                res += backtrack(i + 1, record, n);
            }
        }
        return res;
    }

    private boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) return false;
        }
        return true;
    }
}