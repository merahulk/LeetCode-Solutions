class Solution {
    public int numberOfSubstrings(String s) {
        int[] counts = new int[3]; // To track the count of 'a', 'b', and 'c'
        int left = 0;
        int result = 0;
        int n = s.length();
        
        for (int right = 0; right < n; right++) {
            // Include the current character in the window
            counts[s.charAt(right) - 'a']++;
            
            // While the window contains at least one of 'a', 'b', and 'c'
            while (counts[0] > 0 && counts[1] > 0 && counts[2] > 0) {
                // All substrings from 'right' to the end of the string are valid
                result += n - right;
                
                // Shrink the window from the left
                counts[s.charAt(left) - 'a']--;
                left++;
            }
        }
        
        return result;
    }
}