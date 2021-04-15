package algorithms;

public class ReplaceO {

    public char[][] replace(char[][] chars) {
        if (chars.length == 0 || chars[0].length == 0) {
            return chars;
        }
        int x = 0;
        int y = 0;

        while (x < chars[0].length) {

            while (y < chars.length) {

            }
        }
        return chars;

    }

    private void dfs(char[][] array, int x, int y) {
        if (x < 0 || y < 0 || x >= array.length || y >= array[0].length || array[x][y] != 'O') {
            return;
        }
        array[x][y] = 'X';
    }

    private void bfs(char[][] array) {

    }
}
