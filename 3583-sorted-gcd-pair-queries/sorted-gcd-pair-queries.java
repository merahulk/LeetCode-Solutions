public class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        int[] freq = new int[maxVal + 1];
        for (int num : nums) {
            freq[num]++;
        }

        long[] exactPairs = new long[maxVal + 1];

        for (int g = maxVal; g >= 1; g--) {
            long countMultiples = 0;
            for (int multiple = g; multiple <= maxVal; multiple += g) {
                countMultiples += freq[multiple];
            }

            long totalPairsWithDivisorG = countMultiples * (countMultiples - 1) / 2;

            for (int multiple = 2 * g; multiple <= maxVal; multiple += g) {
                totalPairsWithDivisorG -= exactPairs[multiple];
            }

            exactPairs[g] = totalPairsWithDivisorG;
        }

        long[] prefixSum = new long[maxVal + 1];
        for (int i = 1; i <= maxVal; i++) {
            prefixSum[i] = prefixSum[i - 1] + exactPairs[i];
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long targetIndex = queries[i];
            
            int low = 1, high = maxVal;
            int resultGCD = maxVal;
            
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (prefixSum[mid] > targetIndex) {
                    resultGCD = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            ans[i] = resultGCD;
        }

        return ans;
    }
}