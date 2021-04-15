package algorithms.tree;

import java.util.Arrays;

/**
 * @author yanwenbo
 */
public class DfsTest {

    private Integer target;

    public DfsTest(Integer t) {
        this.target = t;
    }

    public static void main(String[] args) {
        int[][] a = {{1, 3 ,4 , 6}, {15, 34 ,11 , 9}, {0, 4 ,9 , 9}, {11, 13 ,14 , 16}};
        System.out.println(Arrays.deepToString(a));
        new DfsTest(14).dfs(a, 0, 0);
        System.out.println(Arrays.deepToString(a));
    }

    private static void dfs(int[] a, int[] book, int step) {
        int n = book.length;
        if (step == n) {
            for (int i = 0; i < n; i++) {
                System.out.print(a[i] + "");
            }
            System.out.print("\n");
            return;
        }
        for (int i = 0; i < n; i++) {
            if (book[i] == 0) {
                a[step] = i;
                book[i] = 1;
                dfs(a, book, step + 1);
                book[i] = 0;
            }
        }
    }

    private void dfs(int[][] array, int x, int y) {
        if (x < 0 || y <0 || x >= array.length || y >= array[0].length || array[x][y] != target) {
            return;
        }
        if (array[x][y] == target) {
            array[x][y] += 10000;
            return;
        }
        dfs(array, x + 1, y);
        dfs(array, x - 1, y);
        dfs(array, x, y + 1);
        dfs(array, x, y - 1);
    }
}
