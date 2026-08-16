class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] c = new int[3];
        for (int s : stones) {
            ++c[s % 3];
        }
        // Check both strategies: Alice starts with remainder 1 or remainder 2
        int[] t = new int[] {c[0], c[2], c[1]};
        return check(c) || check(t);
    }
    
    private boolean check(int[] count) {
        // Clone the array to avoid mutating the original count
        int[] c = count.clone();
        if (c[1] == 0) return false;
        
        // Alice takes one stone of remainder 1
        --c[1];
        
        // Calculate the maximum turns possible in the alternating sequence (1, 1, 2, 1, 2...) plus 0s
        int turn = 1 + Math.min(c[1], c[2]) * 2 + c[0];
        
        // If there are leftover remainder 1 stones, Alice can use one more safely
        if (c[1] > c[2]) {
            --c[1];
            ++turn;
        }
        
        // Alice wins if total turns is odd AND there are leftover stones (c[1] != c[2])
        return turn % 2 == 1 && c[1] != c[2];
    }
}