
import java.util.*;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);

        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int x : nums) {
            int a = dp.getOrDefault(x, 0);
            int b = dp.getOrDefault(x + 1, 0);

            dp.put(x + 1, Math.max(dp.getOrDefault(x + 1, 0), a + 1));
            dp.put(x + 2, Math.max(dp.getOrDefault(x + 2, 0), b + 1));

            ans = Math.max(ans, Math.max(dp.get(x + 1), dp.get(x + 2)));
        }

        return ans;
    }
}
