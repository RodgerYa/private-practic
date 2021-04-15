package algorithms;

import java.util.*;

public class Simple {

    public static void main(String[] args) {
        Simple simple = new Simple();
//        simple.testFibonacci();
//        simple.testTwoSum();
        System.out.println(simple.isValid("(("));

    }

    private void testEuclid() {
        Simple simple = new Simple();
        System.out.printf(simple.euclid(319, 377) + "");
    }

    /**
     * 欧几里德算法
     */
    private Integer euclid(Integer a, Integer b) {
        if (b == 0) {
            return a;
        }
        int remainder = a % b;
        return euclid(b, remainder);
    }

    private void testIntRange(int i) {
        System.out.printf(Math.abs(i) + "");
    }

    public void testFibonacci() {
        Integer n = 15;
        System.out.println(fibonacci(n));
    }

    /**
     * 斐波那契数列
     */
    private Integer fibonacci(Integer x) {
        if (x == 0) {
            return 0;
        }
        if (x == 1) {
            return 1;
        }
        Integer i = fibonacci(x - 1) + fibonacci(x - 2);
        return i;
    }

    public void testTwoSum() {
        Simple simple = new Simple();
        int[] nums = {3, 2, 4};
        int target = 6;
        int[] r = simple.twoSum(nums, target);
        System.out.printf("");
    }

    /**
     * 获取两数之和
     */
    private int[] twoSum(int[] nums, int target) {
        int i = 0, j = 0;
        // 双层遍历记录下标
        for (i = 0; i < nums.length; i++) {
            for (j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    /**
     * 整数反转
     */
    private int reverse_(int x) {
        // i j 表示第几位  x y 表示i j位对应的数字 m n 分别表示i j 对应的数量级
        int ans = 0;
        while (x != 0) {
            int pop = x % 10;
            if (ans > Integer.MAX_VALUE / 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (ans < Integer.MIN_VALUE / 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            ans = ans * 10 + pop;
            x /= 10;
        }
        return ans;
    }

    /**
     * 整数反转
     */
    private int reverse(int x) {
        int result = 0;
        int pop;
        while (x != 0) {
            pop = x % 10;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            result = result * 10 + pop;
            x /= 10;
        }
        return result;
    }

    /**
     * 罗马数组转换
     */
    private int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
                result += map.get(s.substring(i, i + 2));
                i++;
            } else {
                result += map.get(s.substring(i, i + 1));
            }
        }
        return result;
    }

    /**
     * 罗马数组转换2
     */
    private String intToRoman(Integer num) {
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < 13) {
            while (num >= nums[index]) {
                num -= nums[index];
                sb.append(roman[index]);
                if (num == 0) {
                    break;
                }
            }
            index++;
        }
        return sb.toString();
    }

    private boolean isValid(String s) {
        if (s.length() % 2 == 1) {
            return false;
        }
        Map<String, String> map = new HashMap<String, String>() {{
            put(")", "(");
            put("}", "{");
            put("]", "[");
        }};

        Stack<String> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            String str = String.valueOf(s.charAt(i));

            if (map.containsValue(str)) {
                stack.push(str);
            }

            if (map.containsKey(str)) {
                // 匹配到右边字符串时栈不允许为空
                if (stack.isEmpty()) {
                    return false;
                }
                if (map.getOrDefault(str, "").equals(stack.pop())) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }


}
