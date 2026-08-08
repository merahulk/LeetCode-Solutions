class Solution {
    public int[] validSequence(String word1, String word2) {

        int n = word1.length();
        int m = word2.length();

        int[] ans = new int[m];

        // last[j] = the index of the last occurrence in word1
        // where word1[index] == word2[j].
        int[] last = new int[m];

        for (int i = 0; i < m; i++) {
            last[i] = -1;
        }

        // Find the latest possible position for each character
        // of word2 while maintaining subsequence order.
        int i = n - 1;
        int j = m - 1;

        while (i >= 0 && j >= 0) {

            if (word1.charAt(i) == word2.charAt(j)) {
                last[j] = i;
                j--;
            }

            i--;
        }

        // Greedily construct the lexicographically smallest
        // sequence of indices.
        boolean canSkip = true;

        i = 0;
        j = 0;

        while (i < n && j < m) {

            // Exact match
            if (word1.charAt(i) == word2.charAt(j)) {

                ans[j] = i;
                j++;

            } else if (canSkip &&
                       (j == m - 1 || i < last[j + 1])) {

                // Use our one allowed mismatch.
                ans[j] = i;
                j++;
                canSkip = false;
            }

            i++;
        }

        // We couldn't construct the whole sequence.
        if (j < m) {
            return new int[0];
        }

        return ans;
    }
}