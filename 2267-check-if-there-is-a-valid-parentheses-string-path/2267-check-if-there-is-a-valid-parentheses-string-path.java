class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Path length must be even
        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        // Must start with '('
        if (grid[0][0] == ')') {
            return false;
        }

        // Must end with ')'
        if (grid[m - 1][n - 1] == '(') {
            return false;
        }

        // +1 prevents ArrayIndexOutOfBoundsException
        boolean[][][] dp = new boolean[m][n][m + n + 1];

        // Starting cell '(' gives balance = 1
        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }

                int change = (grid[i][j] == '(') ? 1 : -1;

                for (int balance = 0; balance <= m + n; balance++) {

                    int previousBalance = balance - change;

                    // Previous balance must be valid
                    if (previousBalance < 0 || previousBalance > m + n) {
                        continue;
                    }

                    // Come from top
                    if (i > 0 && dp[i - 1][j][previousBalance]) {
                        dp[i][j][balance] = true;
                    }

                    // Come from left
                    if (j > 0 && dp[i][j - 1][previousBalance]) {
                        dp[i][j][balance] = true;
                    }
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}