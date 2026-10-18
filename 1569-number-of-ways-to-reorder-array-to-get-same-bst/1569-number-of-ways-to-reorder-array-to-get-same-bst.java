class Solution {
    private static final int MOD = 1_000_000_007;
    private long[][] comb;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        // Precompute binomial coefficients
        comb = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            comb[i][0] = comb[i][i] = 1;

            for (int j = 1; j < i; j++) {
                comb[i][j] = (comb[i - 1][j - 1]
                        + comb[i - 1][j]) % MOD;
            }
        }

        // Subtract the original ordering
        return (int) ((solve(nums) - 1 + MOD) % MOD);
    }

    private long solve(int[] nums) {
        int n = nums.length;

        // A single element has only one possible ordering
        if (n <= 2) {
            return 1;
        }

        int root = nums[0];

        int[] left = new int[n];
        int[] right = new int[n];

        int leftSize = 0;
        int rightSize = 0;

        // Partition elements into left and right subtrees
        for (int i = 1; i < n; i++) {
            if (nums[i] < root) {
                left[leftSize++] = nums[i];
            } else {
                right[rightSize++] = nums[i];
            }
        }

        // Create arrays containing only the relevant elements
        int[] leftArr = new int[leftSize];
        int[] rightArr = new int[rightSize];

        System.arraycopy(left, 0, leftArr, 0, leftSize);
        System.arraycopy(right, 0, rightArr, 0, rightSize);

        // Number of ways to arrange each subtree
        long leftWays = solve(leftArr);
        long rightWays = solve(rightArr);

        // Choose positions for the left subtree elements
        long ways = comb[n - 1][leftSize];

        ways = (ways * leftWays) % MOD;
        ways = (ways * rightWays) % MOD;

        return ways;
    }
}