class Solution {
    public int smallestNumber(int n, int t) {
        for(int i=n; ;i++){
            if(getDigitProduct(i)% t==0){
                return i;
            }
        }
        
    }
    private int getDigitProduct(int num){
        int product=1;
        while(num>0){
            product*=(num%10);
            num/=10;
        }
        return product;
    }

}