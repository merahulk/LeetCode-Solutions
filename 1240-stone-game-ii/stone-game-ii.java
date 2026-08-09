class Solution {
    int [][] memo;
    int[] suffixsum;
    public int stoneGameII(int[] piles) {
        int n=piles.length;
        memo=new int[n][n+1];
        suffixsum=new int[n];

        suffixsum[n-1]=piles[n-1];
        for(int i=n-2;i>=0;i--){
            suffixsum[i]=suffixsum[i+1]+piles[i];
        }
        return dfs(0,1,piles);
        
    }
    private int dfs(int i,int m,int[] piles){
        if(i>=piles.length){
            return 0;
        }
        if(i+2*m>=piles.length){
            return suffixsum[i];
        }
        if(memo[i][m]!=0){
            return memo[i][m];
        }
        int maxstones=0;

        for(int x=1;x<=2*m;x++){
            int opponentstones=dfs(i+x,Math.max(m,x),piles);
            int currentstones=suffixsum[i]-opponentstones;

            maxstones=Math.max(maxstones,currentstones);
        }
        return memo[i][m]=maxstones;
    }
}