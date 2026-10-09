
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int open = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                // Every '(' requires two consecutive ')'
                open++;
            } else {
                // Check whether the next character is also ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++; // Consume the pair '))'
                } else {
                    // Insert one ')' to complete the pair
                    insertions++;
                }

                if (open > 0) {
                    open--;
                } else {
                    // No matching '(' exists; insert one '('
                    insertions++;
                }
            }
        }

        // Each remaining '(' needs two closing parentheses
        insertions += open * 2;

        return insertions;
    }
}
