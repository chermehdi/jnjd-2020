import java.util.*;
import java.io.*;

public class Solution {
    private static int n, x, k;
    private static long[][][][] dp;
    private static final long MOD = (long) 1e9 + 7;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        x = in.nextInt();
        k = in.nextInt();

        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) 
            arr[i] = in.nextInt();

        int less = 0;
        int more = 0;

        for (int i = 0; i < n; ++i) {
            if (arr[i] <= arr[x]) ++less;
            else ++more;
        }

        dp = new long[n][n][k + 1][2];
        for (int i = 0; i < n; ++i) 
            for (int j = 0; j < n; ++j)
                for (int soted = 0; sorted <= k; ++sorted) 
                    dp[i][j][sorted][0] = dp[i][j][sorted][1] = Long.MIN_VALUE;
        
        
        long ans = 0;
        --less;
        for (int i = 0; i < less; ++i) {
            ans = (ans + solve(i, more + less - i - 1, 2, false)) % MOD;
        }

        for (int i = 0; i < more; ++i) {
            ans = (ans + solve (less + more - i - 1, i, 2, true)) % MOD;
        }
        
        if (n == 1) {
            // WELL, x should be 0, and k should be 1 (k <= n)
            ans = 1;
        }

        System.out.println(ans);
    }

    private static long solve(int less, int more, int sorted, boolean inc) {
        if (sorted > k) return 0;
        if (less == 0 && more == 0) return 1;
        long ret = 0;

        if(dp[less][more][sorted][inc ? 1 : 0] != Long.MIN_VALUE)
            return dp[less][more][sorted][inc ? 1 : 0];

        for (int i = 0; i < less; ++i) {
            ret = (ret + solve (i, more + less - i - 1, inc ? 2 : sorted + 1, false)) % MOD;
        }

        for (int i = 0; i < more; ++i) {
            ret = (ret + solve (less + more - i - 1, i, inc ? sorted + 1 : 2, true)) % MOD;
        }

        return dp[less][more][sorted][inc ? 1 : 0] = ret % MOD;
    }
}r
