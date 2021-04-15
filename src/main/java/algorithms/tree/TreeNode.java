package algorithms.tree;

/**
 * @author yanwenbo
 * @date 2021/3/30
 */
public class TreeNode<T> {
    T val;
    TreeNode left;
    TreeNode right;

    TreeNode(T x) {
        val = x;
    }

    public static TreeNode<Integer> getDefaultNode() {
        TreeNode root = new TreeNode(1);
        TreeNode root_left = new TreeNode(2);
        TreeNode root_right = new TreeNode(3);
        TreeNode root_left_left = new TreeNode(4);
        TreeNode root_left_right = new TreeNode(5);
        TreeNode root_left_right_left = new TreeNode(1);
        TreeNode root_left_right_right = new TreeNode(2);

        TreeNode root_right_left = new TreeNode(6);
        TreeNode root_right_left_left = new TreeNode(3);
        TreeNode root_right_left_left_left = new TreeNode(7);
        TreeNode root_right_left_left_right = new TreeNode(8);
        TreeNode root_left_left_left = new TreeNode(9);
        TreeNode root_left_left_right = new TreeNode(0);

        root.left = root_left;
        root_left.left = root_left_left;
        root_left.right = root_left_right;
        root_left_left.left = root_left_left_left;
        root_left_left.right = root_left_left_right;

        root_left_right.left = root_left_right_left;
        root_left_right.right = root_left_right_right;

        root.right = root_right;
        root_right.left = root_right_left;
        root_right_left.left = root_right_left_left;
        root_right_left_left.left = root_right_left_left_left;
        root_right_left_left.right = root_right_left_left_right;
        return root;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
