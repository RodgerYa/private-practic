package algorithms.stackAndQueue;

import java.util.Stack;

public class StackToQueue {

    /**
     * stack to queue
     */
    class CQueue {

        private Stack<Integer> first;
        private Stack<Integer> second;

        public CQueue() {
            first = new Stack<>();
            second = new Stack<>();
        }

        public void appendTail(int value) {
            if (first == null) {
                first = new Stack<>();
            }
            first.push(value);
        }

        public int deleteHead() {
            if (first.isEmpty() && second.isEmpty()) {
                return -1;
            }
            if (!second.isEmpty()) {
                return second.pop();
            }
            while (!first.isEmpty()) {
                second.push(first.pop());
            }
            return second.pop();
        }
    }
}
