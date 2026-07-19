class Solution {
    public String smallestSubsequence(String s) {
        int[] lastIndex= new int[26];
        for(int i=0;i<s.length();i++){
            lastIndex[s.charAt(i)-'a']=i;
        }

        boolean[] visited =new boolean[26];
        
        StringBuilder stack =new StringBuilder();

        for(int i=0;i<s.length();i++){
            char curr =s.charAt(i);
            int currIdx=curr-'a';

            if(visited[currIdx]){
                continue;
            }
            while(stack.length()>0 && stack.charAt(stack.length()-1)>curr){
                char top=stack.charAt(stack.length()-1);
                int topIdx=top-'a';

                if(lastIndex[topIdx]<i){
                    break;
                }
                stack.deleteCharAt(stack.length()-1);
                visited[topIdx]=false;
            }
            stack.append(curr);
            visited[currIdx]=true;

        }
        return stack.toString();
    }
}