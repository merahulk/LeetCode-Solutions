class Solution {
    public String digitSum(String s, int k) {

        while(s.length()>k){
            StringBuilder newString = new StringBuilder();

            for(int i=0;i<s.length();i+=k){
                int end= Math.min(i+k,s.length());
                String group=s.substring(i,end);

                int sum=0;
                for(int j=0;j<group.length();j++){
                    sum+=group.charAt(j)-'0';
                }
                newString.append(sum);
            }
            s=newString.toString();
        }
        return s;
        
    }
}