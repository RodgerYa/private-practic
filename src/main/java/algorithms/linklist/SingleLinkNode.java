package algorithms.linklist;

/**
 * @author yanwenbo
 * @date 2021/3/30
 */
public class SingleLinkNode {
    SingleLinkNode next;
    int val;

    public SingleLinkNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    public static SingleLinkNode defaultLink() {
        SingleLinkNode node = new SingleLinkNode(1);
        SingleLinkNode node_next = new SingleLinkNode(2);
        SingleLinkNode node_next_next = new SingleLinkNode(3);
        SingleLinkNode node_next_next_next = new SingleLinkNode(4);
        SingleLinkNode node_next_next_next_next = new SingleLinkNode(5);
        node.next = node_next;
        node_next.next = node_next_next;
        node_next_next.next = node_next_next_next;
        node_next_next_next.next = node_next_next_next_next;
        return node;
    }

    public static void print(SingleLinkNode node) {
        String s = node.toString();
        while (node.next != null) {
            s += String.format("->%s", node.next.val);
            node = node.next;
        }
        System.out.println(s);;
    }
}
