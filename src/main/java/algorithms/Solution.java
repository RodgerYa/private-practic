package algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] arr = {"5","-2","4","C","D","9","+","+"};
//        solution.calPoints(arr);
//        solution.backspaceCompare("y#fo##f", "y#f#o##f");
//        solution.simplifyPath("/.");
//        solution.decodeString("3[a]");
        solution.removeKdigits_("100200", 1);
    }

    public int calPoints(String[] ops) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        for(int i = 0; i < ops.length; i++) {
            String op = ops[i];
            switch (op) {
                case "C":
                    if (stack.empty()) {
                        throw new RuntimeException("分数不足一轮");
                    }
                    stack.pop();
                    break;
                case "D":
                    if (stack.size() < 1) {
                        throw new RuntimeException("分数不足两轮");
                    }
                    Integer a = stack.peek();
                    stack.push(a * 2);
                    break;
                case "+":
                    if (stack.size() < 2) {
                        throw new RuntimeException("分数不足两轮");
                    }
                    Integer c = stack.pop();
                    Integer b = stack.peek();
                    stack.push(c);
                    stack.push(c + b);
                    break;
                default:
                    try {
                        stack.push(Integer.valueOf(op));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
            }
        }
        while(!stack.empty()) {
            result += stack.pop();
        }
        return result;
    }
    public boolean backspaceCompare(String S, String T) {
        return backspaceString(S).equals(backspaceString(T));
    }

    public static String backspaceString(String str) {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            String string = String.valueOf(str.charAt(i));
            // 必须使用 equals 比较字符串
            boolean isBackspace = "#".equals(string);
            if (isBackspace && !stack.empty()) {
                stack.pop();
            } else if (!isBackspace) {
                // # 不入栈
                stack.push(string);
            }
        }
        StringBuilder s = new StringBuilder();
        while(!stack.empty()) {
            s.append(stack.pop());
        }
        return s.toString();
    }

    public String removeOuterParentheses(String S) {
        return doRemove(S);
    }

    private static String doRemove(String str) {
        List<String> list = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String s = String.valueOf(str.charAt(i));
            sb.append(s);
            if (")".equals(s) && right(sb.toString())) {
                int length = sb.toString().length();
                sb.delete(length - 1, length);
                sb.delete(0, 1);
                list.add(sb.toString());
                sb.delete(0, sb.toString().length());
            }
        }
        return list.stream().reduce((s1, s2) -> s1 + s2).orElse("");
    }

    private static boolean right(String s) {
        if (s.length() == 0 || s.length() % 2 != 0) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if ("(".equals(s)) {
                count++;
            }
        }
        return false;
    }
    public String simplifyPath(String path) {
        StringBuilder sb = new StringBuilder();
        Stack<String> stack = new Stack<>();
        for(char c: path.toCharArray()) {
            if (c == '/') {
                if (!stack.empty() && "/".equals(stack.peek())) {
                    continue;
                }
                if (!stack.empty() && ".".equals(stack.peek())) {
                    stack.pop();
                    continue;
                }
            }
            if (c == '.' && !stack.empty() && ".".equals(stack.peek())) {
                int count = 0;
                while(!stack.empty() && count < 2) {
                    String s = stack.pop(); // 删除当前路径名
                    if ("/".equals(s)) {
                        count++;
                    }
                }
                continue;
            }
            stack.push(String.valueOf(c));
        }
        while(!stack.empty()) {
            sb.append(stack.pop());
        }
        String result = sb.reverse().toString().replace("./", "");
        int length = result.length();
        if (result.contains(".")) {
            result = result.replace(".", "");
        }
        if (result.isEmpty() || "/".equals(result)) {
            result = "/";
        } else if ("/".equals(result.substring(length - 1, length))) {
            result = result.substring(0, length - 1);
        }
        return result;
    }
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        Integer count = 0;
        Stack<String> stringStack = new Stack();
        Stack<Integer> numStack = new Stack();
        for (char c: s.toCharArray()) {
            if (c >= '0' || c <= '9') {
                count = count * 10 + (c - 48);
            } else if (c == '[') {
                stringStack.push(sb.toString());
                sb.delete(0, sb.toString().length());
                numStack.push(count);
                count = 0;
            } else if (c == ']') {
                int n = numStack.pop();
                String str = sb.toString();
                while (--n > 0) {
                    sb.append(str);
                }
                sb = new StringBuilder(stringStack.pop() + sb.toString());
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public String removeKdigits(String num, int k) {
        if (num.length() <= k) {
            return "0";
        }
        if (k == 0) {
            return num;
        }
        Stack<Integer> stack = new Stack<>();
        int count = 0;
        for (char c : num.toCharArray()) {
            int i = c - 48;
            if (stack.empty() || stack.peek() <= i) {
                stack.push(i);
            } else {
                while (!stack.empty() && stack.peek() > i) {
                    int top = stack.pop();
                    if (++count == k) {
                        break;
                    }
                }
                stack.push(i);
            }
        }
        if (stack.empty()) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(stack.pop());
        }
        String result = sb.reverse().toString();
        while (result.startsWith("0")) {
            result = result.substring(1, result.length());
        }
        if (count < k) {
            result = result.substring(result.length() - k + count, result.length());
        }
        if (result.isEmpty()) {
            result = "0";
        }
        return result;
    }

    public String removeKdigits_(String num, int k) {
        if (num.length() <= k) {
            return "0";
        }
        if (k == 0) {
            return num;
        }
        char[] chars = num.toCharArray();
        int index = 0;
        StringBuilder str = new StringBuilder();
        while (index < chars.length && k > 0) {
            Integer i = chars[index] - 48;
            if (str.length() == 0  || Integer.parseInt(str.substring(str.length() - 1)) <= i) {
                str.append(i);
                index++;
            } else {
                str.deleteCharAt(str.length() - 1);
                k--;
            }
        }
        // 如果index < chars.length 需要将剩余字符拼接
        if(index < chars.length) {
            for (int i = index; i < chars.length; i++) {
                str.append(chars[i]);
            }
        } else if (k > 0) {
            // 如果 k > 0 需要将最后k个字符删除
            str.delete(str.length() - k, str.length());
        }
        // 清除前置0 如果全为0则保留一个0
        while (str.toString().startsWith("0") && str.length() > 1) {
            str.delete(0, 1);
        }
        return str.toString();
    }
}