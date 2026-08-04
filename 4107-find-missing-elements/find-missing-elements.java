class Solution {
    public List<Integer> findMissingElements(int[] nums) {

        int minval=Integer.MAX_VALUE;
        int maxval=Integer.MIN_VALUE;

        HashSet<Integer> set= new HashSet<>();


        for(int num:nums){
            minval=Math.min(minval,num);
            maxval=Math.max(maxval,num);
            set.add(num);
        }

        List<Integer> result= new ArrayList<>();

        for(int i=minval;i<=maxval;i++){
            if(!set.contains(i)){
                result.add(i);
            }
        }
        return result;
        
    }
}