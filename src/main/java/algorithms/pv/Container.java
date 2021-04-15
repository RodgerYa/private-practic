package algorithms.pv;

import java.util.LinkedList;

/**
 * @author yanwenbo
 * @date 2021/2/26
 */
public class Container {
    private final LinkedList<Integer> queue = new LinkedList<>();
    private Integer capacity = 10;

    public Container(Integer capacity) {
        this.capacity = capacity;
    }

    public void put(Integer value) {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                if (queue.size() >= capacity) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.add(value);
                this.notifyAll();
            }
        }
    }

    public Integer take() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                if (queue.isEmpty()) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.removeFirst();
                this.notifyAll();
            }
        }
    }
}
