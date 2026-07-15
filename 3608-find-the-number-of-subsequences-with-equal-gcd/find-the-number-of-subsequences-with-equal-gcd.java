class Solution {
    // The problem asks us to return the answer modulo 10^9 + 7
    private static final int MOD = 1000000007;
    
    // This is our "memory pad". 
    // It stores results for [index][gcd1][gcd2]
    private Integer[][][] memo;

    public int subsequencePairCount(int[] nums) {
        int n = nums.length;
        
        // The max value in nums is 200, so the max GCD is 200.
        // We use size 201 so we can safely use index 0 up to 200.
        // 0 will represent an "empty" subsequence.
        memo = new Integer[n][201][201];
        
        // Start from index 0, with seq1 and seq2 both being empty (GCD = 0)
        return solve(0, 0, 0, nums);
    }

    private int solve(int index, int gcd1, int gcd2, int[] nums) {
        // BASE CASE: We reached the end of the array
        if (index == nums.length) {
            // If both sequences aren't empty (gcd > 0) AND their GCDs match
            if (gcd1 > 0 && gcd2 > 0 && gcd1 == gcd2) {
                return 1; // We found 1 valid pair!
            }
            return 0; // Not a valid pair
        }

        // If we have already solved this exact situation, use the saved answer
        if (memo[index][gcd1][gcd2] != null) {
            return memo[index][gcd1][gcd2];
        }

        long totalWays = 0;

        // CHOICE 1: Skip the current number (don't add to seq1 or seq2)
        totalWays = (totalWays + solve(index + 1, gcd1, gcd2, nums)) % MOD;

        // CHOICE 2: Add the current number to seq1
        // If seq1 is empty (gcd1 == 0), the new GCD is just the number itself.
        // Otherwise, we calculate the new GCD.
        int nextGcd1 = (gcd1 == 0) ? nums[index] : calculateGCD(gcd1, nums[index]);
        totalWays = (totalWays + solve(index + 1, nextGcd1, gcd2, nums)) % MOD;

        // CHOICE 3: Add the current number to seq2
        int nextGcd2 = (gcd2 == 0) ? nums[index] : calculateGCD(gcd2, nums[index]);
        totalWays = (totalWays + solve(index + 1, gcd1, nextGcd2, nums)) % MOD;

        // Save the result in our memory pad before returning
        // We cast it back to int because modulo ensures it fits in an integer
        memo[index][gcd1][gcd2] = (int) totalWays;
        return memo[index][gcd1][gcd2];
    }

    // A standard helper method to find the Greatest Common Divisor of two numbers
    private int calculateGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return calculateGCD(b, a % b);
    }
}