class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] count = new int[3];

        // Count stones based on remainder when divided by 3
        for (int stone : stones) {
            count[stone % 3]++;
        }

        int c0 = count[0];
        int c1 = count[1];
        int c2 = count[2];

        // If number of remainder-0 stones is even
        if (c0 % 2 == 0) {
            return c1 > 0 && c2 > 0;
        }

        // If number of remainder-0 stones is odd
        return Math.abs(c1 - c2) >= 3;
    }
}