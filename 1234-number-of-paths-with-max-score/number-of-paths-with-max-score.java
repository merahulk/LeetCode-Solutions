import java.util.List;

class Solution {
    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int MOD = 1_000_000_007;

        // dpScore[r][c] stores the max sum to reach (r, c)
        // dpPaths[r][c] stores the number of ways to reach (r, c) with that max sum
        int[][] dpScore = new int[n][n];
        int[][] dpPaths = new int[n][n];

        // Initialize all scores to -1 (meaning unreachable)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dpScore[i][j] = -1;
            }
        }

        // Base case: Starting at 'E' (top-left corner)
        dpScore[0][0] = 0;
        dpPaths[0][0] = 1;

        // Look-back directions: Up, Left, Diagonal Top-Left
        int[][] dirs = {{-1, 0}, {0, -1}, {-1, -1}};

        for (int r = 0; r < n; r++) {
            String rowStr = board.get(r);
            for (int c = 0; c < n; c++) {
                char cell = rowStr.charAt(c);

                // Skip obstacles and the top-left starting corner
                if (cell == 'X' || (r == 0 && c == 0)) {
                    continue;
                }

                int maxScore = -1;

                // Step 1: Find the maximum score among valid incoming neighbors
                for (int[] dir : dirs) {
                    int prevR = r + dir[0];
                    int prevC = c + dir[1];

                    if (prevR >= 0 && prevC >= 0 && dpScore[prevR][prevC] != -1) {
                        if (dpScore[prevR][prevC] > maxScore) {
                            maxScore = dpScore[prevR][prevC];
                        }
                    }
                }

                // Step 2: If at least one incoming neighbor was reachable
                if (maxScore != -1) {
                    int currVal = (cell == 'S') ? 0 : (cell - '0');
                    dpScore[r][c] = maxScore + currVal;

                    // Step 3: Sum paths from neighbors that match the maximum score
                    long totalPaths = 0;
                    for (int[] dir : dirs) {
                        int prevR = r + dir[0];
                        int prevC = c + dir[1];

                        if (prevR >= 0 && prevC >= 0 && dpScore[prevR][prevC] == maxScore) {
                            totalPaths = (totalPaths + dpPaths[prevR][prevC]) % MOD;
                        }
                    }
                    dpPaths[r][c] = (int) totalPaths;
                }
            }
        }

        // The final result is collected at the bottom-right corner 'S'
        int finalScore = dpScore[n - 1][n - 1];
        int finalPaths = dpPaths[n - 1][n - 1];

        if (finalScore == -1) {
            return new int[]{0, 0};
        }

        return new int[]{finalScore, finalPaths};
    }
}