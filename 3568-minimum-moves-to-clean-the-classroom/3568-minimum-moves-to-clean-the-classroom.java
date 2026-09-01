import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int sr = -1, sc = -1;
        int litterCount = 0;

        // Give every litter cell a bit number
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        // No litter to collect
        if (litterCount == 0) {
            return 0;
        }

        int allCollected = (1 << litterCount) - 1;

        /*
         * visited[r][c][energy][mask]
         *
         * Since energy can be 0..energy
         * and mask can be 0..(2^litterCount - 1)
         */
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        Queue<State> queue = new ArrayDeque<>();

        visited[sr][sc][energy][0] = true;
        queue.offer(new State(sr, sc, energy, 0, 0));

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            State cur = queue.poll();

            int r = cur.r;
            int c = cur.c;
            int e = cur.energy;
            int mask = cur.mask;
            int moves = cur.moves;

            // All litter collected
            if (mask == allCollected) {
                return moves;
            }

            // If energy is 0, we can only continue if we are on R.
            if (e == 0 && classroom[r].charAt(c) != 'R') {
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // Outside grid
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                // Obstacle
                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Every move costs 1 energy
                int newEnergy = e - 1;

                // We cannot make a move without energy
                if (newEnergy < 0) {
                    continue;
                }

                // Collect litter if present
                int newMask = mask;

                if (classroom[nr].charAt(nc) == 'L') {
                    int id = litterId[nr][nc];
                    newMask |= (1 << id);
                }

                // Reset energy on R
                if (classroom[nr].charAt(nc) == 'R') {
                    newEnergy = energy;
                }

                if (!visited[nr][nc][newEnergy][newMask]) {
                    visited[nr][nc][newEnergy][newMask] = true;

                    queue.offer(
                        new State(
                            nr,
                            nc,
                            newEnergy,
                            newMask,
                            moves + 1
                        )
                    );
                }
            }
        }

        return -1;
    }

    static class State {
        int r, c;
        int energy;
        int mask;
        int moves;

        State(int r, int c, int energy, int mask, int moves) {
            this.r = r;
            this.c = c;
            this.energy = energy;
            this.mask = mask;
            this.moves = moves;
        }
    }
}