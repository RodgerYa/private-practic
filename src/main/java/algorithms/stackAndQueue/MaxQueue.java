package algorithms.stackAndQueue;

import java.util.LinkedList;

class MaxQueue {

    public static void main(String[] args) {
        MaxQueue maxQueue = new MaxQueue();
        maxQueue.push_back(1);
        maxQueue.push_back(2);
        maxQueue.pop_front();
        maxQueue.push_back(0);
        maxQueue.pop_front();
        maxQueue.push_back(5);
    }

    private LinkedList<Integer> queue;
    private LinkedList<Integer> helper;

    public MaxQueue() {
        this.queue = new LinkedList<Integer>();
        this.helper = new LinkedList<Integer>();
    }

    public int max_value() {
        if (helper.size() == 0) {
            return -1;
        }
        return helper.peekLast();
    }

    public void push_back(int value) {
        queue.addLast(value);
        if (helper.size() == 0 || helper.peekLast() <= value) {
            helper.addLast(value);
        }
    }

    public int pop_front() {
        if (queue.size() == 0) {
            return -1;
        }
        Integer e = queue.removeFirst();
        if (e.equals(helper.peekFirst())) {
            helper.removeFirst();
        }
        return e;
    }
}

/**
 * Your MaxQueue object will be instantiated and called as such:
 * MaxQueue obj = new MaxQueue();
 * int param_1 = obj.max_value();
 * obj.push_back(value);
 * int param_3 = obj.pop_front();
 */