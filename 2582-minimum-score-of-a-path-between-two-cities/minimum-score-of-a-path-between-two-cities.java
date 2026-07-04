class Solution {
    public int minScore(int n, int[][] roads) {
        
        List<List<int[]>> graph = new ArrayList<>();
        for(int i =0; i<=n;i++){
            graph.add(new ArrayList<>());
        }

        for(int[] road :roads){
            int u =road[0];
            int v =road[1];
            int distance = road[2];
            graph.get(u).add(new int[]{v,distance});
            graph.get(v).add(new int[]{u,distance});

        }
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited  =new boolean[n+1];

        queue.offer(1);
        visited[1]=true;
        int minScore=Integer.MAX_VALUE;

        while(!queue.isEmpty()){
            int node=queue.poll();

            for(int[] neighborInfo: graph.get(node)){
                int neighbor = neighborInfo[0];
                int distance = neighborInfo[1];

                minScore = Math.min(minScore,distance);

                if(!visited[neighbor]){
                    visited[neighbor]= true;
                    queue.offer(neighbor);
                }

            }
        }
        return minScore;
    }
}