
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        // Build original tree
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Find nearest matching ancestor
        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] previous = new int[n];
        int[] newParent = new int[n];
        int[] order = new int[n];

        int[] stack = new int[2 * n];
        int top = 0;
        int count = 0;

        stack[top++] = 0;

        while (top > 0) {
            int val = stack[--top];

            if (val >= n) {
                int node = val - n;
                int ch = s.charAt(node) - 'a';

                last[ch] = previous[node];
                continue;
            }

            int node = val;
            int ch = s.charAt(node) - 'a';

            previous[node] = last[ch];

            newParent[node] = (node == 0)
                    ? -1
                    : (last[ch] == -1 ? parent[node] : last[ch]);

            last[ch] = node;
            order[count++] = node;

            stack[top++] = node + n;

            for (int child : tree[node]) {
                stack[top++] = child;
            }
        }

        // Build modified tree
        List<Integer>[] modified = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            modified[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            modified[newParent[i]].add(i);
        }

        // Calculate subtree sizes
        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        int[] traversal = new int[n];
        int idx = 0;
        top = 0;

        stack[top++] = 0;

        while (top > 0) {
            int node = stack[--top];
            traversal[idx++] = node;

            for (int child : modified[node]) {
                stack[top++] = child;
            }
        }

        for (int i = idx - 1; i > 0; i--) {
            int node = traversal[i];
            ans[newParent[node]] += ans[node];
        }

        return ans;
    }
}
