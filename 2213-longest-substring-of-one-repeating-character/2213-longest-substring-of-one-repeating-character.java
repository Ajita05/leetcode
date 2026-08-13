import java.util.*;

class Solution {

    TreeMap<Integer, Integer> runs = new TreeMap<>();
    TreeMap<Integer, Integer> lengths = new TreeMap<>();
    char[] s;

    public int[] longestRepeating(String str, String queryCharacters, int[] queryIndices) {

        s = str.toCharArray();

        // Build initial runs
        int start = 0;

        for (int i = 1; i <= s.length; i++) {

            if (i == s.length || s[i] != s[i - 1]) {
                runs.put(start, i - 1);
                addLength(i - start);
                start = i;
            }
        }

        int[] answer = new int[queryCharacters.length()];

        for (int q = 0; q < queryCharacters.length(); q++) {

            int index = queryIndices[q];
            char newChar = queryCharacters.charAt(q);

            update(index, newChar);

            answer[q] = lengths.lastKey();
        }

        return answer;
    }

    // Add a run length to the frequency map
    private void addLength(int len) {
        lengths.put(len, lengths.getOrDefault(len, 0) + 1);
    }

    // Remove a run length from the frequency map
    private void removeLength(int len) {

        int count = lengths.get(len);

        if (count == 1) {
            lengths.remove(len);
        } else {
            lengths.put(len, count - 1);
        }
    }

    // Add a run [l, r]
    private void addRun(int l, int r) {

        runs.put(l, r);
        addLength(r - l + 1);
    }

    // Remove a run [l, r]
    private void removeRun(int l, int r) {

        runs.remove(l);
        removeLength(r - l + 1);
    }

    private void update(int index, char newChar) {

        if (s[index] == newChar) {
            return;
        }

        /*
         * Find the run containing index.
         */
        int start = runs.floorKey(index);
        int end = runs.get(start);

        /*
         * Remove the old run.
         */
        removeRun(start, end);

        /*
         * Split the old run around index.
         *
         * Example:
         *
         * aaaaa
         *   ^
         *
         * becomes:
         *
         * aa + a + aa
         */
        if (start < index) {
            addRun(start, index - 1);
        }

        if (index < end) {
            addRun(index + 1, end);
        }

        /*
         * Change the character.
         */
        s[index] = newChar;

        /*
         * Add the new single-character run.
         */
        addRun(index, index);

        /*
         * Now merge with the left run if it has
         * the same character.
         */
        Integer leftStart = runs.lowerKey(index);

        if (leftStart != null) {

            int leftEnd = runs.get(leftStart);

            if (s[leftEnd] == s[index]) {

                removeRun(leftStart, leftEnd);
                removeRun(index, index);

                addRun(leftStart, index);

                index = leftStart;
            }
        }

        /*
         * Merge with the right run if it has
         * the same character.
         */
        Integer rightStart = runs.higherKey(index);

        if (rightStart != null) {

            int rightEnd = runs.get(rightStart);

            if (s[rightStart] == s[index]) {

                removeRun(index, runs.get(index));
                removeRun(rightStart, rightEnd);

                addRun(index, rightEnd);
            }
        }
    }
}