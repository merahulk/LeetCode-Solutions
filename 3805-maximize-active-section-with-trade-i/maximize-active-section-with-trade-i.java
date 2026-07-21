class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n =s.length();

        int totalOnes=0;
        for(int i=0;i<n;i++){
            if(s.charAt(i)=='1'){
                totalOnes++;
            }
        }
        String t="1" +s+"1";
        int tLength=t.length();

        int maxGain=0;
        int i=0;

        while(i<tLength){
            if(t.charAt(i)=='1'){
                int startOne=i;
                while(i<tLength && t.charAt(i)=='1'){
                    i++;

                }
                int endOne=i-1;
                if(startOne>0 && endOne<tLength-1){
                    int L=0;
                    int leftIdx=startOne-1;
                    while(leftIdx >=0 && t.charAt(leftIdx)=='0'){

                        L++;
                        leftIdx--;
                    }
                    int R=0;
                    int rightIdx=endOne+1;
                    while(rightIdx<tLength && t.charAt(rightIdx)=='0'){
                        R++;
                        rightIdx++;

                    }
                    if(L>0 && R>0){
                        maxGain = Math.max(maxGain,L+R);
                    }
                }
            }
                else{
                    i++;
                }
        }
                return totalOnes+maxGain;
        }
        
    }