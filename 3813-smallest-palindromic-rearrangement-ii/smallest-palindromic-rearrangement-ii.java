class Solution {
    private static final int MAX_VAL = 1_000_000_001;
    private long[][] C;

    public String smallestPalindrome(String s, int k) {
        int n = s.length();
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int halfLen = n / 2;
        
        // 1. Precompute C(n, r) using Pascal's Triangle capped at MAX_VAL
        buildPascalTable(halfLen + 1);

        int[] halfCount = new int[26];
        String mid = "";
        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
            if (count[i] % 2 != 0) {
                mid = String.valueOf((char) ('a' + i));
            }
        }

        StringBuilder leftHalf = new StringBuilder();

        // 2. Build left half character by character
        for (int i = 0; i < halfLen; i++) {
            boolean placed = false;
            int remLen = halfLen - 1 - i;

            for (int c = 0; c < 26; c++) {
                if (halfCount[c] > 0) {
                    halfCount[c]--;
                    long ways = countPermutations(halfCount, remLen, k);
                    
                    if (ways >= k) {
                        leftHalf.append((char) ('a' + c));
                        placed = true;
                        break;
                    } else {
                        k -= ways;
                        halfCount[c]++; // Backtrack
                    }
                }
            }

            if (!placed) {
                return "";
            }
        }

        String left = leftHalf.toString();
        String right = new StringBuilder(left).reverse().toString();
        return left + mid + right;
    }

    private void buildPascalTable(int maxN) {
        C = new long[maxN + 1][maxN + 1];
        for (int i = 0; i <= maxN; i++) {
            C[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                C[i][j] = Math.min((long) MAX_VAL, C[i - 1][j - 1] + C[i - 1][j]);
            }
        }
    }

    private long countPermutations(int[] counts, int len, int cap) {
        long res = 1;
        int remLen = len;

        for (int c : counts) {
            if (c <= 0) continue;

            long nCr = C[remLen][c];

            // Safely multiply res * nCr with cap check
            if (res > 0 && cap / res < nCr) {
                return cap;
            }

            res *= nCr;
            remLen -= c;
        }

        return res;
    }
}