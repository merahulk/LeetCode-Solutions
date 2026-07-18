class Solution {
    public int findGCD(int[] nums) {

        int min=nums[0];
        int max=nums[0];

        for(int num:nums){
            if(num<min){
                min=num;
            }
            if(num>max){
                max=num;
            }
        }
        return getGCD(min,max);
    }
    private int getGCD(int a,int b){
        while(b!=0){
            int remainder=a%b;
            a=b;
            b=remainder;
        }
        return a;
    }
}