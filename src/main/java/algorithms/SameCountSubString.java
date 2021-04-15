package algorithms;

public class SameCountSubString {

    class Solution {

        public int countBinarySubstrings(String s) {
            int i = 0, result = 0, last = 0;
            while(i < s.length()) {
                char c = s.charAt(i);
                int count = 0;
                while (i < s.length() && s.charAt(i) == c) {
                    i++;
                    count++;
                }
                result += Math.min(last, count);
                last = count;
            }
            return  result;
        }


    }
}
