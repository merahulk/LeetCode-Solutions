class Solution {
    public int reverseDegree(String s) {
        int totaldegree=0;

        for(int i=0;i<s.length();i++){
            char ch=s.charAt(i);

            int stringindex=i+1;
            int revalphabetindex=26-(ch-'a');

            totaldegree+=revalphabetindex*stringindex;

        }
        return totaldegree;
        
    }
}