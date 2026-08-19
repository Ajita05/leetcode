class Solution {
public:
    int maxNumberOfFamilies(int n, vector<vector<int>>& reservedSeats) {
        
        // Store reserved seats for each row
        unordered_map<int, unordered_set<int>> reserved;

        for (auto &seat : reservedSeats) {
            reserved[seat[0]].insert(seat[1]);
        }

        int ans = (n - reserved.size()) * 2;

        // Check only rows that have reserved seats
        for (auto &[row, seats] : reserved) {
            
            bool left = true;    // seats 2,3,4,5
            bool middle = true;  // seats 4,5,6,7
            bool right = true;   // seats 6,7,8,9

            // Check left block
            for (int s = 2; s <= 5; s++) {
                if (seats.count(s)) {
                    left = false;
                    break;
                }
            }

            // Check middle block
            for (int s = 4; s <= 7; s++) {
                if (seats.count(s)) {
                    middle = false;
                    break;
                }
            }

            // Check right block
            for (int s = 6; s <= 9; s++) {
                if (seats.count(s)) {
                    right = false;
                    break;
                }
            }

            if (left && right) {
                // Both non-overlapping groups can be placed
                ans += 2;
            }
            else if (left || middle || right) {
                // At least one group can be placed
                ans += 1;
            }
        }

        return ans;
    }
};