class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int diff = 0;
        int questionDiff = 0;

        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?') {
                questionDiff++;
            } else {
                diff += num.charAt(i) - '0';
            }
        }

        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?') {
                questionDiff--;
            } else {
                diff -= num.charAt(i) - '0';
            }
        }

        // If the number of '?' is odd, Alice can always make
        // the difference non-zero.
        if (questionDiff % 2 != 0) {
            return true;
        }

        // Bob can win only if the existing difference can be
        // exactly balanced by the '?' positions.
        return diff != -9 * questionDiff / 2;
    }
}