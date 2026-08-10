class Solution {
    public boolean isGood(int[] nums) {
        int n=0;

        for(int num:nums){
            n=Math.max(n,num);
        }
        if(nums.length!=n+1){
            return false;
        }
        Map<Integer,Integer>counts=new HashMap<>();
        for(int num:nums){
            counts.put(num,counts.getOrDefault(num,0)+1);
        }

        for(int i=1;i<=n;i++){
            if(i==n){
                if(counts.getOrDefault(i,0)!=2)
                return false;
            }else{
                if(counts.getOrDefault(i,0)!=1)
                return false;
            }

        }
        return true;
        
    }
}