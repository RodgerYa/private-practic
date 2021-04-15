package algorithms.pv;

/**
 * @author yanwenbo
 * @date 2021/2/26
 */
public class Consumer implements Runnable {

    private Container container;

    public Consumer(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        container.take();
    }
}
