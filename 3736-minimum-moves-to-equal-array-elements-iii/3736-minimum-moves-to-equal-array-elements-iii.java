class Solution {
    public int minMoves(int[] nums) {
        int max = nums[0];

        // Find the maximum element
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, nums[i]);
        }

        int moves = 0;

        // Calculate moves required to make all elements equal
        for (int i = 0; i < nums.length; i++) {
            moves += max - nums[i];
        }

        return moves;
    }
}