
import java.util.*;

class Solution {
    int n, LOG, timer;
    List<Integer>[] adj;
    int[] tin, tout, depth, bit;
    int[][] up;
    char[] chars;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                        String s, String[] queries) {
        this.n = n;
        chars = s.toCharArray();

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) {
            LOG++;
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[LOG][n];
        bit = new int[n + 2];
        timer = 0;

        buildTree();

        // Each character contributes to the root-path
        // masks of every node in its subtree.
        for (int u = 0; u < n; u++) {
            int mask = 1 << (chars[u] - 'a');
            rangeXor(tin[u], tout[u], mask);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.trim().split("\\s+");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');

                if (oldMask != newMask) {
                    rangeXor(tin[u], tout[u],
                             oldMask ^ newMask);
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int lca = getLCA(u, v);

                int mask = pointQuery(tin[u])
                         ^ pointQuery(tin[v])
                         ^ (1 << (chars[lca] - 'a'));

                // At most one character may have odd frequency.
                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    void buildTree() {
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        int[] stackNode = new int[n];
        int[] stackIndex = new int[n];

        int top = 0;
        stackNode[0] = 0;
        stackIndex[0] = 0;
        parent[0] = 0;

        tin[0] = ++timer;
        up[0][0] = 0;

        for (int j = 1; j < LOG; j++) {
            up[j][0] = 0;
        }

        while (top >= 0) {
            int u = stackNode[top];

            if (stackIndex[top] < adj[u].size()) {
                int v = adj[u].get(stackIndex[top]++);

                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;

                tin[v] = ++timer;
                up[0][v] = u;

                for (int j = 1; j < LOG; j++) {
                    up[j][v] = up[j - 1][up[j - 1][v]];
                }

                top++;
                stackNode[top] = v;
                stackIndex[top] = 0;
            } else {
                tout[u] = timer;
                top--;
            }
        }
    }

    int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    void fenwickAdd(int index, int mask) {
        while (index <= n + 1) {
            bit[index] ^= mask;
            index += index & -index;
        }
    }

    void rangeXor(int left, int right, int mask) {
        fenwickAdd(left, mask);
        fenwickAdd(right + 1, mask);
    }

    int pointQuery(int index) {
        int mask = 0;

        while (index > 0) {
            mask ^= bit[index];
            index -= index & -index;
        }

        return mask;
    }
}
