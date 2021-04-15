package algorithms.tree;

import org.apache.commons.compress.utils.Lists;

import java.util.*;

/**
 * @author yanwenbo
 * @date 2021/3/30
 */
public class BinaryTree {

    private List<TreeNode> nodes = Lists.newArrayList();
    private List<List<TreeNode>> levelNodes = Lists.newArrayList();

    public static void main(String[] args) {
        // 打印二叉树
        TreePrinter.printNode(TreeNode.getDefaultNode());

        BinaryTree tree = new BinaryTree();


//        tree.preOrder(TreeNode.getDefaultNode());
//        tree.midOrder(TreeNode.getDefaultNode());
//        tree.postOrder(TreeNode.getDefaultNode());
//        System.out.println(tree.nodes);
        tree.listOfDepth(TreeNode.getDefaultNode());
        System.out.println("");
//        System.out.println(tree.levelNodes);

    }

    public void levelOrder(TreeNode root) {
        Queue<TreeNode> q = new ArrayDeque<>();
        if (root != null) {
            q.offer(root);
        }
        while (!q.isEmpty()) {
            List<TreeNode> level = new ArrayList<>();
            int size = q.size();
            while (size-- > 0) {
                TreeNode node = q.poll();
                level.add(node);
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            if (!level.isEmpty()) {
                levelNodes.add(level);
            }
        }
    }

    /**
     * 奇数层和偶数层按照反方向遍历二叉树
     * - 核心在于区分当前节点是哪一层，因此需要对奇数层偶数层节点做隔离
     * - 奇数层和偶数层的数据保证顺序排列 按照相反的方向遍历节点
     * 1、使用两个双端队列分别记录奇数、偶数层
     * 2、奇数层遍历 从左往右
     * 3、偶数层遍历 从右往左
     * 4、不论哪一层遍历后都需要将下一层的节点按照正常顺序保存在对方队列中
     */
    public void levelOrder_(TreeNode root) {
        // 奇数层节点
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        // 偶数层节点
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        if (root != null) {
            queue1.offerFirst(root);
        }
        while (!queue1.isEmpty()) {
            // 当前层所有节点的遍历结果
            List<TreeNode> levelNodes = new ArrayList<>();
            while (!queue1.isEmpty()) {
                // 辅助队列 保存正序的原始数据
                LinkedList<TreeNode> help_queue = new LinkedList<>(queue1);
                // 1.奇数层从左往右遍历
                while (!queue1.isEmpty()) {
                    levelNodes.add(queue1.pollFirst());
                }
                if (!levelNodes.isEmpty()) {
                    this.levelNodes.add(levelNodes);
                }
                // 2.下一层的节点 按照正序排列
                putNodes(help_queue, queue2);
            }
            while (!queue2.isEmpty()) {
                LinkedList<TreeNode> help_queue = new LinkedList<>(queue2);
                // 3.偶数层从右往左遍历
                while (queue2.isEmpty()) {
                    levelNodes.add(queue2.pollLast());
                }
                if (!levelNodes.isEmpty()) {
                    this.levelNodes.add(levelNodes);
                }
                // 4.下一层的节点 按照正序排列
                putNodes(help_queue, queue1);
            }
        }
    }

    private void putNodes(LinkedList<TreeNode> producer, LinkedList<TreeNode> consumer) {
        while (!producer.isEmpty()) {
            TreeNode node = producer.pollFirst();
            if (node.left != null) {
                consumer.offer(node.left);
            }
            if (node.right != null) {
                consumer.offer(node.right);
            }
        }
    }

    /**
     * 判断是否是平衡二叉树 是-输出深度 否-输出-1
     */
    public int isBalanced(TreeNode root) {
        int left_deep = 0;
        int right_deep = 0;
        int deep = 0;
        if (root == null) {
            return deep;
        }
        if (root.left != null) {
            left_deep += isBalanced(root.left);
        }
        if (left_deep == -1) {
            return -1;
        }
        if (root.right != null) {
            right_deep += isBalanced(root.right);
        }
        if (right_deep == -1) {
            return -1;
        }
        if (Math.abs(left_deep - right_deep) > 1) {
            return -1;
        }
        deep = Math.max(left_deep, right_deep) + 1;
        return deep;
    }

    /**
     * 计算二叉树深度
     */
    public int treeDeep(TreeNode root) {
        int left_deep = 0;
        int right_deep = 0;
        int deep = 0;
        if (root == null) {
            return deep;
        }
        if (root.left != null) {
            left_deep += treeDeep(root.left);
        }
        if (root.right != null) {
            right_deep += treeDeep(root.right);
        }
        deep = Math.max(left_deep, right_deep) + 1;
        return deep;
    }

    /**
     * 前序遍历
     */
    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        nodes.add(root);
        if (root.left != null) {
            preOrder(root.left);
        }
        if (root.right != null) {
            preOrder(root.right);
        }
    }

    /**
     * 中序遍历
     */
    public void midOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            midOrder(root.left);
        }
        nodes.add(root);
        if (root.right != null) {
            midOrder(root.right);
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            postOrder(root.left);
        }
        if (root.right != null) {
            postOrder(root.right);
        }
        nodes.add(root);
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode[] listOfDepth(TreeNode<Integer> tree) {
        ArrayList<ListNode> result = new ArrayList<ListNode>();
        Queue<TreeNode<Integer>> queue = new ArrayDeque<>();
        if (tree != null) {
            queue.offer(tree);
        }
        while (!queue.isEmpty()) {
            ListNode head = new ListNode(queue.poll().val);
            ListNode helpHead = new ListNode(0);
            helpHead.next = head;
            int size = queue.size();
            while (size-- > 0) {
                TreeNode<Integer> treeNode = queue.poll();
                ListNode listNode = new ListNode(treeNode.val);
                head.next = listNode;
                head = listNode;
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
            }
            result.add(helpHead.next);

        }
        ListNode[] nodes = result.toArray(new ListNode[0]);
        return nodes;
    }
}
