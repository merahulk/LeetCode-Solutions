class Solution {
    public int missingMultiple(int[] nums, int k) {
        Set<Integer> numSet =new HashSet<>();
        for(int num:nums){
            numSet.add(num);
        }
        int multiple=k;
        while(true){
            if(!numSet.contains(multiple)){
                return multiple;
            }
            multiple+=k;

        }
        
    }
}