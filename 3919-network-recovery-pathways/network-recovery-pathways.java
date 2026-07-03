import java.util.*;

class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        
        // Build the adjacency list: graph.get(u) -> list of [v, cost]
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        int minCost = Integer.MAX_VALUE;
        int maxCost = -1;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];
            graph.get(u).add(new int[]{v, cost});
            minCost = Math.min(minCost, cost);
            maxCost = Math.max(maxCost, cost);
        }
        
        // Binary search for the maximum possible minimum-edge-cost
        int low = minCost, high = maxCost;
        int ans = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (check(mid, graph, online, k, n)) {
                ans = mid;       // 'mid' is achievable, try to find a larger minimum
                low = mid + 1;
            } else {
                high = mid - 1;  // 'mid' is too high, scale down
            }
        }
        
        return ans;
    }
    
    private boolean check(int minEdgeConstraint, List<List<int[]>> graph, boolean[] online, long k, int n) {
        // dp[i] will store the minimum path cost from node i to node n-1
        // Using Double/Long to handle unvisited states safely.
        Long[] dp = new Long[n];
        long totalCost = dfs(0, minEdgeConstraint, graph, online, dp, n);
        
        return totalCost <= k;
    }
    
    private long dfs(int u, int minEdgeConstraint, List<List<int[]>> graph, boolean[] online, Long[] dp, int n) {
        // Destination reached successfully
        if (u == n - 1) {
            return 0;
        }
        
        // If already computed, return the cached result
        if (dp[u] != null) {
            return dp[u];
        }
        
        long minCostToTarget = Long.MAX_VALUE;
        
        for (int[] next : graph.get(u)) {
            int v = next[0];
            int edgeCost = next[1];
            
            // 1. Skip if the edge doesn't meet our binary search minimum threshold
            // 2. Skip if the next node is offline (destination n-1 is always online)
            if (edgeCost < minEdgeConstraint || (!online[v] && v != n - 1)) {
                continue;
            }
            
            long nextPathCost = dfs(v, minEdgeConstraint, graph, online, dp, n);
            if (nextPathCost != Long.MAX_VALUE) {
                minCostToTarget = Math.min(minCostToTarget, edgeCost + nextPathCost);
            }
        }
        
        return dp[u] = minCostToTarget;
    }
}