class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1000000007;
        int n = s.length();

        // dp[i] = number of distinct subsequences (including empty)
        // using first i characters
        long[] dp = new long[n + 1];

        dp[0] = 1; // empty subsequence

        // Stores dp value before the previous occurrence of each character
        long[] last = new long[26];

        for (int i = 1; i <= n; i++) {
            char ch = s.charAt(i - 1);
            int index = ch - 'a';

            dp[i] = (2 * dp[i - 1]) % MOD;

            // Remove duplicates created by the previous occurrence
            dp[i] = (dp[i] - last[index] + MOD) % MOD;

            // Store the old dp value for this character
            last[index] = dp[i - 1];
        }

        // Remove the empty subsequence
        return (int) ((dp[n] - 1 + MOD) % MOD);
    }
}