class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<List<Integer>> graph =new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }
        for(int[] edge:edges){
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        boolean[] visited =new boolean[n];
        int completeComponentsCount=0;

        for(int i=0;i<n;i++){
            if(!visited[i]){
                if(isCompleteComponent(i,graph,visited)){
                    completeComponentsCount++;
                }

            }
        }
        return completeComponentsCount;
        
    }
    private boolean isCompleteComponent(int startNode,List<List<Integer>>graph,boolean[] visited){
        Queue<Integer> queue =new LinkedList<>();
        queue.offer(startNode);
        visited[startNode]=true;

        int nodeCount=0;
        int totalEdgesCount=0;

        while(!queue.isEmpty()){
            int current=queue.poll();
            nodeCount++;

            totalEdgesCount+=graph.get(current).size();

            for(int neighbor:graph.get(current)){
                if(!visited[neighbor]){
                    visited[neighbor]=true;
                    queue.offer(neighbor);
                }
            }
        }
        return totalEdgesCount==(nodeCount*(nodeCount-1));
    }
}