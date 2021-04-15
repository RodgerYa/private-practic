package algorithms.search;

public class BinarySearch {

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch(new Integer[]{1, 2, 3, 4, 5, 6});
        System.out.println(binarySearch.search(6));
    }

    private Integer[] array;
    private Integer count = 0;

    public BinarySearch(Integer[] arr) {
        this.array = arr;
    }

    public Integer search(Integer target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            count++;
            int index = (left + right) / 2;
            Integer mid = array[index];
            if (mid > target) {
                right = index - 1;
                continue;
            }
            if (mid < target) {
                left = index + 1;
                continue;
            }
            System.out.println("count = " + count);
            return index;
        }
        System.out.println("count = " + count);
        return -1;
    }
}
