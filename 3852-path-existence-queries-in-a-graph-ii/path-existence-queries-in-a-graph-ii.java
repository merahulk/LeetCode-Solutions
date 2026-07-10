public class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        // Step 1: Pair each element with its original index and sort by value
        int[][] sorted = new int[n][2];
        for (int i = 0; i < n; i++) {
            sorted[i][0] = nums[i];
            sorted[i][1] = i;
        }
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Map original index to its position in the sorted array
        int[] pos = new int[n];
        for (int i = 0; i < n; i++) {
            pos[sorted[i][1]] = i;
        }
        
        // Step 2: Precompute the farthest right jump for each sorted position using two pointers
        int LOG = 18; // 2^17 = 131,072 > 10^5
        int[][] st = new int[n][LOG];
        
        int right = 0;
        for (int i = 0; i < n; i++) {
            while (right < n && sorted[right][0] - sorted[i][0] <= maxDiff) {
                right++;
            }
            st[i][0] = right - 1; // Farthest index reachable in 1 hop
        }
        
        // Step 3: Fill the binary lifting table
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                st[i][j] = st[st[i][j - 1]][j - 1];
            }
        }
        
        // Step 4: Process each query in O(log n) time
        int[] answer = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            
            if (u == v) {
                answer[i] = 0;
                continue;
            }
            
            int a = pos[u];
            int b = pos[v];
            if (a > b) { // Ensure we always jump from left to right
                int temp = a;
                a = b;
                b = temp;
            }
            
            int curr = a;
            int steps = 0;
            
            // Greedily jump as far as possible staying strictly to the left of target b
            for (int j = LOG - 1; j >= 0; j--) {
                if (st[curr][j] < b) {
                    curr = st[curr][j];
                    steps += (1 << j);
                }
            }
            
            // Check if one final hop can cover or land on the target b
            if (st[curr][0] >= b) {
                answer[i] = steps + 1;
            } else {
                answer[i] = -1; // Disconnected components
            }
        }
        
        return answer;
    }
}