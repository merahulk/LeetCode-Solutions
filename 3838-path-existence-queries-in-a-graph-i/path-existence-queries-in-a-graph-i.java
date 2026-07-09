class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        
        int[] islandId = new int[n];
        int currentIsland = 0;
        islandId[0] = currentIsland;
        
        for (int i = 1; i < n; i++) {
            if (nums[i] - nums[i - 1] > maxDiff) {
                currentIsland++;
            }
            islandId[i] = currentIsland;
        }
        
        boolean[] answer = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            answer[i] = (islandId[u] == islandId[v]);
        }
        
        return answer;
    }
}
        
