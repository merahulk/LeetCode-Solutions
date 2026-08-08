class Solution {

    private int[][] dp;

    public String smallestNumber(String num, long t) {

        int[] req = new int[4];
        int[] primes = {2, 3, 5, 7};

        // Factorize t
        for (int i = 0; i < 4; i++) {
            while (t % primes[i] == 0) {
                req[i]++;
                t /= primes[i];
            }
        }

        // t contains a prime factor other than 2, 3, 5, 7
        if (t > 1) {
            return "-1";
        }

        // DP for factors 2 and 3
        buildDP(req[0], req[1]);

        int n = num.length();

        // ---------------------------------------------------------
        // Check whether num itself is valid
        // ---------------------------------------------------------
        int[] total = new int[4];
        int zeroCount = 0;

        for (int i = 0; i < n; i++) {

            int d = num.charAt(i) - '0';

            if (d == 0) {
                zeroCount++;
            } else {
                addFactors(d, total);
            }
        }

        if (zeroCount == 0 && satisfies(total, req)) {
            return num;
        }

        // ---------------------------------------------------------
        // Try to construct an answer with the same length.
        // ---------------------------------------------------------

        int[] prefix = total.clone();

        // zeroCount currently represents zeros in the whole number.

        for (int i = n - 1; i >= 0; i--) {

            int currentDigit = num.charAt(i) - '0';

            // Remove num[i] from the prefix.
            if (currentDigit == 0) {
                zeroCount--;
            } else {
                removeFactors(currentDigit, prefix);
            }

            // Prefix must be zero-free.
            if (zeroCount > 0) {
                continue;
            }

            // Try every larger digit.
            for (int d = currentDigit + 1; d <= 9; d++) {

                int[] current = prefix.clone();
                addFactors(d, current);

                int remaining = n - i - 1;

                if (!canComplete(current, req, remaining)) {
                    continue;
                }

                // -------------------------------------------------
                // Construct smallest possible suffix.
                // -------------------------------------------------

                StringBuilder ans = new StringBuilder(n);

                ans.append(num, 0, i);
                ans.append((char) ('0' + d));

                int[] factors = current;

                for (int pos = 0; pos < remaining; pos++) {

                    int slotsLeft = remaining - pos - 1;

                    for (int candidate = 1; candidate <= 9; candidate++) {

                        int[] next = factors.clone();

                        addFactors(candidate, next);

                        if (canComplete(next, req, slotsLeft)) {

                            ans.append((char) ('0' + candidate));
                            factors = next;

                            break;
                        }
                    }
                }

                return ans.toString();
            }
        }

        // ---------------------------------------------------------
        // Same length is impossible.
        // Try a longer length.
        // ---------------------------------------------------------

        int length = Math.max(n + 1, minimumLength(req));

        while (true) {

            StringBuilder ans = new StringBuilder(length);

            int[] factors = new int[4];

            boolean possible = true;

            for (int pos = 0; pos < length; pos++) {

                int remaining = length - pos - 1;
                boolean found = false;

                for (int d = 1; d <= 9; d++) {

                    int[] next = factors.clone();

                    addFactors(d, next);

                    if (canComplete(next, req, remaining)) {

                        ans.append((char) ('0' + d));
                        factors = next;

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                return ans.toString();
            }

            length++;
        }
    }

    // -------------------------------------------------------------
    // Add factors of a digit.
    // factors = [2, 3, 5, 7]
    // -------------------------------------------------------------
    private void addFactors(int d, int[] f) {

        switch (d) {

            case 2:
                f[0]++;
                break;

            case 3:
                f[1]++;
                break;

            case 4:
                f[0] += 2;
                break;

            case 5:
                f[2]++;
                break;

            case 6:
                f[0]++;
                f[1]++;
                break;

            case 7:
                f[3]++;
                break;

            case 8:
                f[0] += 3;
                break;

            case 9:
                f[1] += 2;
                break;
        }
    }

    // -------------------------------------------------------------
    // Remove factors of a digit.
    // -------------------------------------------------------------
    private void removeFactors(int d, int[] f) {

        switch (d) {

            case 2:
                f[0]--;
                break;

            case 3:
                f[1]--;
                break;

            case 4:
                f[0] -= 2;
                break;

            case 5:
                f[2]--;
                break;

            case 6:
                f[0]--;
                f[1]--;
                break;

            case 7:
                f[3]--;
                break;

            case 8:
                f[0] -= 3;
                break;

            case 9:
                f[1] -= 2;
                break;
        }
    }

    // -------------------------------------------------------------
    // Check whether current factors satisfy t.
    // -------------------------------------------------------------
    private boolean satisfies(int[] current, int[] req) {

        for (int i = 0; i < 4; i++) {

            if (current[i] < req[i]) {
                return false;
            }
        }

        return true;
    }

    // -------------------------------------------------------------
    // DP for factors 2 and 3.
    // -------------------------------------------------------------
    private void buildDP(int max2, int max3) {

        int INF = 1_000_000;

        dp = new int[max2 + 1][max3 + 1];

        for (int i = 0; i <= max2; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        // 2 -> 2
        // 3 -> 3
        // 4 -> 2^2
        // 6 -> 2 * 3
        // 8 -> 2^3
        // 9 -> 3^2
        int[][] factors = {
            {1, 0},
            {0, 1},
            {2, 0},
            {1, 1},
            {3, 0},
            {0, 2}
        };

        for (int i = 0; i <= max2; i++) {

            for (int j = 0; j <= max3; j++) {

                if (dp[i][j] == INF) {
                    continue;
                }

                for (int[] f : factors) {

                    int ni = Math.min(max2, i + f[0]);
                    int nj = Math.min(max3, j + f[1]);

                    dp[ni][nj] = Math.min(
                        dp[ni][nj],
                        dp[i][j] + 1
                    );
                }
            }
        }
    }

    // -------------------------------------------------------------
    // Check whether remaining slots can satisfy the requirement.
    // -------------------------------------------------------------
    private boolean canComplete(
            int[] current,
            int[] req,
            int slots) {

        int need2 = Math.max(0, req[0] - current[0]);
        int need3 = Math.max(0, req[1] - current[1]);
        int need5 = Math.max(0, req[2] - current[2]);
        int need7 = Math.max(0, req[3] - current[3]);

        // 5 requires digit 5.
        // 7 requires digit 7.
        int fixed = need5 + need7;

        if (fixed > slots) {
            return false;
        }

        int remaining = slots - fixed;

        return dp[need2][need3] <= remaining;
    }

    // -------------------------------------------------------------
    // Minimum length required to construct t.
    // -------------------------------------------------------------
    private int minimumLength(int[] req) {

        return req[2] + req[3] + dp[req[0]][req[1]];
    }
}