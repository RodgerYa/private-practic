package algorithms.linklist;

/**
 * @author yanwenbo
 * @date 2021/2/4
 */
public class ReverseLinkList {

    public static void main(String[] args) {
        SingleLinkNode head = SingleLinkNode.defaultLink();
        SingleLinkNode.print(head);
        new ReverseLinkList().reverse(head);
    }

    public void reverse(SingleLinkNode head) {
        if (head == null || head.next == null) {
            return;
        }
        SingleLinkNode next = head.next;
        head.next = null;
        while (next != null) {
            SingleLinkNode node = next.next;
            next.next = head;
            head = next;
            next = node;
        }
        SingleLinkNode.print(head);
    }
}
