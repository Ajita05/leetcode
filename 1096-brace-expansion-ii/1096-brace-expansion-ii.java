import java.util.*;

class Solution {

    private String expression;
    private int index;

    public List<String> braceExpansionII(String expression) {
        this.expression = expression;
        this.index = 0;

        Set<String> result = parseExpression();

        List<String> list = new ArrayList<>(result);
        Collections.sort(list);

        return list;
    }

    // Handles union
    private Set<String> parseExpression() {
        Set<String> result = new HashSet<>();

        result.addAll(parseConcatenation());

        while (index < expression.length() &&
               expression.charAt(index) == ',') {

            index++; // skip comma
            result.addAll(parseConcatenation());
        }

        return result;
    }

    // Handles concatenation
    private Set<String> parseConcatenation() {
        Set<String> result = new HashSet<>();

        // Empty string initially
        result.add("");

        while (index < expression.length()) {

            char ch = expression.charAt(index);

            // End of current expression
            if (ch == '}' || ch == ',') {
                break;
            }

            Set<String> current;

            if (ch == '{') {

                index++; // skip {

                current = parseExpression();

                index++; // skip }

            } else {

                // Lowercase letter
                index++;

                current = new HashSet<>();
                current.add(String.valueOf(ch));
            }

            // Concatenate both sets
            Set<String> temp = new HashSet<>();

            for (String a : result) {
                for (String b : current) {
                    temp.add(a + b);
                }
            }

            result = temp;
        }

        return result;
    }
}