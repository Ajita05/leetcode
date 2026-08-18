class Solution {
public:
    int largestInteger(vector<int>& nums, int k) {
        int n = nums.size();

        // count[x] = number of subarrays of size k
        // in which x appears
        int count[51] = {0};

        // Generate every subarray of size k
        for (int i = 0; i <= n - k; i++) {

            // seen[x] tells whether x has already appeared
            // in the current subarray
            bool seen[51] = {false};

            for (int j = i; j < i + k; j++) {
                seen[nums[j]] = true;
            }

            // Count each number only once for this subarray
            for (int x = 0; x <= 50; x++) {
                if (seen[x]) {
                    count[x]++;
                }
            }
        }

        // Find the largest number appearing in exactly one subarray
        for (int x = 50; x >= 0; x--) {
            if (count[x] == 1) {
                return x;
            }
        }

        return -1;
    }
};