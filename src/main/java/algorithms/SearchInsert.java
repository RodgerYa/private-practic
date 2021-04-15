package algorithms;

/**
 * @author yanwenbo
 * @date 2021/1/12
 */
public class SearchInsert {

    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4, 8};
//        new SearchInsert().searchInsert_(num, 7);
//        System.out.println(new SearchInsert().myAtoi("-2147483647"));
        System.out.println(-1*(Integer.MIN_VALUE%10));
    }

    public int searchInsert_(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int middle = 0, left = 0, right = nums.length;
        while (left < right) {
            middle = (left + right) / 2;
            if (target == nums[middle]) {
                return middle;
            }
            if (target < nums[middle]) {
                right = middle - 1;
                continue;
            }
            if (target > nums[middle]) {
                left = middle + 1;
            }
        }
        if (nums[left] >= target) {
            return left;
        }
        if (nums[right] <= target) {
            return right;
        }
        return -1;
    }

    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int middle = 0, left = 0, right = nums.length - 1;
        while (left <= right) {
            middle = (left + right) / 2;
            if (target < nums[middle]) {
                right = middle - 1;
                continue;
            }
            if (target > nums[middle]) {
                left = middle + 1;
                continue;
            }
            return middle;
        }
        return -1;
    }

    public int reverse(int x) {
        int result = 0, pop = 0;
        while (x != 0) {
            pop = x % 10;
            x /= 10;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && pop > 8)) {
                return 0;
            }
            result = result * 10 + pop;
        }
        return result;
    }

    public int myAtoi(String s) {
        char[] arr = s.toCharArray();
        int i = 0;
        // 去掉前导空格
        while (i < arr.length && arr[i] == ' ') {
            i++;
        }
        // 合法参数校验
        if (i == arr.length) {
            return 0;
        }
        // 符号判断
        int sign = 0;
        if (arr[i] == '+') {
            i++;
        } else if (arr[i] == '-') {
            i++;
            sign--;
        }
        // 数字转换 溢出处理
        int result = 0;
        while (i < arr.length && isNum(arr[i])) {
            int num = arr[i] - '0';
            i++;
            if (result>Integer.MAX_VALUE/10 || (result == Integer.MAX_VALUE/10 && num>Integer.MAX_VALUE%10)) {
                return Integer.MAX_VALUE;
            }
            if (result<Integer.MIN_VALUE/10 || (result == Integer.MIN_VALUE/10 && num>(-1)*Integer.MIN_VALUE%10)) {
                return Integer.MIN_VALUE;
            }
            // 带符号累加
            result = result * 10 + sign * num;
        }
        return result;
    }

    private boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }
}
