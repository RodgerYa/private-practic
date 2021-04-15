package algorithms.stackAndQueue;

import java.util.Stack;

class MinStack {

    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> helper = new Stack<>();

    /**
     * initialize your data structure here.
     */
    public MinStack() {

    }

    public void push(int x) {
        stack.push(x);

        if (helper.isEmpty() || helper.peek() >= x) {
            helper.push(x);
        }
    }

    public void pop() {
        int i = this.top();
        stack.pop();
        if (i == helper.peek()) {
            helper.pop();
        }
    }

    public int top() {
        if (stack.isEmpty()) {
            throw new RuntimeException("栈为空");
        }
        return stack.peek();
    }

    public int getMin() {
        if (helper.isEmpty()) {
            throw new RuntimeException("栈为空");
        }
        return helper.peek();
    }
}
