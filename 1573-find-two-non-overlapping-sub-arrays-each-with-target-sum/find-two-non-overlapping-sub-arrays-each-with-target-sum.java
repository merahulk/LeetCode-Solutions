
class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        // minLen[i] stores the minimum length of a sub-array with sum = target ending at or before index i
        int[] minLen = new int[n];
        Arrays.fill(minLen, Integer.MAX_VALUE);
        
        int left = 0;
        int currentSum = 0;
        int ans = Integer.MAX_VALUE;
        int bestSoFar = Integer.MAX_VALUE;
        
        for (int right = 0; right < n; right++) {
            currentSum += arr[right];
            
            // Shrink the window if the sum exceeds target
            while (currentSum > target) {
                currentSum -= arr[left];
                left++;
            }
            
            // If we found a valid sub-array equal to target
            if (currentSum == target) {
                int currLen = right - left + 1;
                
                // If there is a valid non-overlapping sub-array to the left
                if (left > 0 && minLen[left - 1] != Integer.MAX_VALUE) {
                    ans = Math.min(ans, currLen + minLen[left - 1]);
                }
                
                // Update the minimum sub-array length found so far
                bestSoFar = Math.min(bestSoFar, currLen);
            }
            
            minLen[right] = bestSoFar;
        }
        
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}