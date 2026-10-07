import java.util.*;

class Solution {

    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();

        // Queue for BFS
        Queue<String> queue = new LinkedList<>();

        // To avoid processing the same string multiple times
        Set<String> visited = new HashSet<>();

        queue.offer(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {

            String current = queue.poll();

            // If current string is valid, add it to result
            if (isValid(current)) {
                result.add(current);
                found = true;
            }

            // Once valid strings are found at this level,
            // don't generate strings with more removals
            if (found) {
                continue;
            }

            // Remove one parenthesis at every possible position
            for (int i = 0; i < current.length(); i++) {

                // We only remove parentheses, not letters
                if (current.charAt(i) != '(' &&
                    current.charAt(i) != ')') {
                    continue;
                }

                String next = current.substring(0, i)
                        + current.substring(i + 1);

                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }

        return result;
    }

    // Checks whether the parentheses are valid
    private boolean isValid(String s) {

        int balance = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                balance++;
            } 
            else if (c == ')') {
                balance--;

                // More ')' than '(' at any point
                if (balance < 0) {
                    return false;
                }
            }
        }

        // Valid only if all '(' have been matched
        return balance == 0;
    }
}