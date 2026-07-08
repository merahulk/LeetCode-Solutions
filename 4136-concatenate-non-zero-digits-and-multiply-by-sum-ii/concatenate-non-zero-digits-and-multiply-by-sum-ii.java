class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        int m = s.length();
        long MOD = 1000000007L;

        long[] pow10 = new long[m + 1];
        pow10[0] = 1;
        for (int i = 1; i <= m; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        int[] nzDigits = new int[m];
        int[] nzPos = new int[m];
        int nzCount = 0;

        for (int i = 0; i < m; i++) {
            if (s.charAt(i) != '0') {
                nzDigits[nzCount] = s.charAt(i) - '0';
                nzPos[nzCount] = i;
                nzCount++;
            }
        }

        long[] prefSum = new long[nzCount + 1];
        long[] prefX = new long[nzCount + 1];

        for (int i = 0; i < nzCount; i++) {
            prefSum[i + 1] = prefSum[i] + nzDigits[i];
            prefX[i + 1] = (prefX[i] * 10 + nzDigits[i]) % MOD;
        }

        int[] nextNzIdx = new int[m];
        int currentNz = nzCount - 1;
        for (int i = m - 1; i >= 0; i--) {
            if (s.charAt(i) != '0') {
                while (currentNz >= 0 && nzPos[currentNz] >= i) {
                    currentNz--;
                }
                nextNzIdx[i] = currentNz + 1;
            } else {
                nextNzIdx[i] = (i == m - 1) ? nzCount : nextNzIdx[i + 1];
            }
        }

        int[] answer = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int L = queries[q][0];
            int R = queries[q][1];

            int compStart = nextNzIdx[L];
            int compEnd = (R == m - 1) ? nzCount - 1 : nextNzIdx[R + 1] - 1;

            if (compStart > compEnd || compStart >= nzCount) {
                answer[q] = 0;
                continue;
            }

            long digitSum = prefSum[compEnd + 1] - prefSum[compStart];
            long len = compEnd - compStart + 1;
            long x = (prefX[compEnd + 1] - (prefX[compStart] * pow10[(int)len]) % MOD + MOD) % MOD;

            long queryAns = (x * (digitSum % MOD)) % MOD;
            answer[q] = (int) queryAns;
        }

        return answer;
    }
}