class Solution {
    public int uniqueXorTriplets(int[] nums) {
        
        Set<Integer> uniqueNumsSet=new HashSet<>();
        for(int num:nums){
            uniqueNumsSet.add(num);
        }

        int[] uniqueNums=new int[uniqueNumsSet.size()];
        int idx=0;
        for(int num:uniqueNumsSet){
            uniqueNums[idx++]=num;
        }

        Set<Integer> pairXors= new HashSet<>();

        for(int i=0;i<uniqueNums.length;i++){
            for(int j=i;j<uniqueNums.length;j++){
                pairXors.add(uniqueNums[i]^ uniqueNums[j]);
            }
        }

        Set<Integer> tripleXors=new HashSet<>();
        for(int pairXor:pairXors){
            for(int num:uniqueNums){
                tripleXors.add(pairXor^num);
            }
        }
        return tripleXors.size();
    }
}