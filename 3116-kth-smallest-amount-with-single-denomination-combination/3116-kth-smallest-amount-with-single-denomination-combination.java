class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long left = 1;
        long right = 1L * coins[0] * k;

        // Binary search
        while (left < right) {
            long mid = left + (right - left) / 2;

            if (count(mid, coins) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // Count numbers <= x divisible by at least one coin
    private long count(long x, int[] coins) {
        int n = coins.length;
        long total = 0;

        // Inclusion-Exclusion
        for (int mask = 1; mask < (1 << n); mask++) {
            long lcm = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    lcm = getLCM(lcm, coins[i]);

                    // If LCM becomes larger than x,
                    // it contributes nothing.
                    if (lcm > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) continue;

            long contribution = x / lcm;

            if (bits % 2 == 1) {
                total += contribution;
            } else {
                total -= contribution;
            }
        }

        return total;
    }

    private long getLCM(long a, long b) {
        return a / gcd(a, b) * b;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}