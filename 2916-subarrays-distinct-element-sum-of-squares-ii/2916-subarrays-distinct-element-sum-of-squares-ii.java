class Solution {
    private static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;

        SegmentTree seg = new SegmentTree(n);
        int[] lastSeen = new int[100001];

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            // Add 1 to distinct counts of subarrays
            // ending at i whose start is after the previous occurrence.
            int prev = lastSeen[x];

            seg.update(prev, i, 1);

            // Sum of squared distinct counts for all subarrays
            // ending at index i.
            ans = (ans + seg.querySumSquares()) % MOD;

            lastSeen[x] = i + 1;
        }

        return (int) ans;
    }

    class SegmentTree {
        int n;
        long[] sum;
        long[] sumSquares;
        long[] lazy;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            sumSquares = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int left, int right, long val) {
            long len = right - left + 1L;

            // (x + val)^2 = x^2 + 2*x*val + val^2
            sumSquares[node] =
                (sumSquares[node]
                + 2L * val % MOD * sum[node] % MOD
                + len % MOD * val % MOD * val % MOD) % MOD;

            sum[node] = (sum[node] + len * val) % MOD;
            lazy[node] += val;
        }

        void push(int node, int left, int right) {
            if (lazy[node] == 0 || left == right) {
                return;
            }

            int mid = (left + right) / 2;

            apply(node * 2, left, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, right, lazy[node]);

            lazy[node] = 0;
        }

        void update(int node, int left, int right,
                    int ql, int qr, long val) {

            if (ql > right || qr < left) {
                return;
            }

            if (ql <= left && right <= qr) {
                apply(node, left, right, val);
                return;
            }

            push(node, left, right);

            int mid = (left + right) / 2;

            update(node * 2, left, mid, ql, qr, val);
            update(node * 2 + 1, mid + 1, right, ql, qr, val);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

            sumSquares[node] =
                (sumSquares[node * 2] + sumSquares[node * 2 + 1]) % MOD;
        }

        void update(int left, int right, long val) {
            if (left > right) {
                return;
            }

            update(1, 0, n - 1, left, right, val);
        }

        long querySumSquares() {
            return sumSquares[1];
        }
    }
}