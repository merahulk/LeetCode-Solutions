class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {

        List<List<Integer>> graph=new ArrayList<>();
        for(int i=0;i<n;i++){
            graph.add(new ArrayList<>());
        }

        for(int[] inv:invocations){
            graph.get(inv[0]).add(inv[1]);

        }

        boolean[] isSuspicious= new boolean[n];
        Queue<Integer> queue =new LinkedList<>();

        queue.offer(k);
        isSuspicious[k]=true;
        
        while(!queue.isEmpty()){
            int current=queue.poll();
            for(int neighbor:graph.get(current)){
                if(!isSuspicious[neighbor]){
                    isSuspicious[neighbor]=true;
                    queue.offer(neighbor);
                }
            }
        }
        boolean canRemove=true;
        for(int[] inv:invocations){
            int u=inv[0];
            int v=inv[1];
            if(!isSuspicious[u]&& isSuspicious[v]){
                canRemove=false;
                break;
            }

        }

        List<Integer> result=new ArrayList<>();
        if(canRemove){
            for(int i=0;i<n;i++){
                if(!isSuspicious[i]){
                    result.add(i);
                }
            }
        }else{
            for(int i=0;i<n;i++){
                result.add(i);
            }

        }
        return result;
    }
}