class Solution {
    public String smallestPalindrome(String s) {
        int halfLen=s.length()/2;

        char[] half=s.substring(0,halfLen).toCharArray();

        java.util.Arrays.sort(half);

        String left=new String(half);
        String right=new StringBuilder(left).reverse().toString();

        if(s.length()%2!=0){
            return left+s.charAt(halfLen)+right;
        }
        return left+right;


        
    }
}