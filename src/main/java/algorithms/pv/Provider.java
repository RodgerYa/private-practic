package algorithms.pv;

import java.util.Random;

/**
 * @author yanwenbo
 * @date 2021/2/26
 */
public class Provider implements Runnable {

    private Container container;

    public Provider(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        container.put(new Random().nextInt(10));
    }
}
