class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        char[] ans = new char[n];

        // Try to match target from left to right
        for (int i = 0; i < n; i++) {
            int x = target.charAt(i) - 'a';

            if (cnt[x] > 0) {
                ans[i] = target.charAt(i);
                cnt[x]--;
            } else {
                // Find smallest character greater than target[i]
                for (int c = x + 1; c < 26; c++) {
                    if (cnt[c] > 0) {
                        ans[i] = (char) ('a' + c);
                        cnt[c]--;

                        fill(ans, i + 1, cnt);
                        return new String(ans);
                    }
                }

                break;
            }
        }

        // Target itself was formed or current position cannot be increased.
        // Backtrack to find the rightmost position we can increase.
        for (int i = n - 1; i >= 0; i--) {
            int[] f = new int[26];

            for (char c : s.toCharArray()) {
                f[c - 'a']++;
            }

            boolean ok = true;

            for (int j = 0; j < i; j++) {
                int x = target.charAt(j) - 'a';

                if (f[x] == 0) {
                    ok = false;
                    break;
                }

                f[x]--;
            }

            if (!ok) continue;

            int x = target.charAt(i) - 'a';

            for (int c = x + 1; c < 26; c++) {
                if (f[c] > 0) {
                    char[] res = target.toCharArray();
                    res[i] = (char) ('a' + c);
                    f[c]--;

                    fill(res, i + 1, f);
                    return new String(res);
                }
            }
        }

        return "";
    }

    private void fill(char[] arr, int start, int[] cnt) {
        int pos = start;

        for (int c = 0; c < 26; c++) {
            while (cnt[c] > 0) {
                arr[pos++] = (char) ('a' + c);
                cnt[c]--;
            }
        }
    }
}