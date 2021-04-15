package algorithms;

public class DeadLock {

    private static final Object obj1 = new Object();
    private static final Object obj2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

    static class Thread1 extends Thread {

        @Override
        public void run() {
            System.out.println(String.format("Thread [%s] running...", Thread.currentThread().getName()));
            try {
                synchronized (obj1) {
                    System.out.println(String.format("Thread [%s] get lock obj1", Thread.currentThread().getName()));
                    Thread.sleep(2000);

                    synchronized (obj2) {
                        System.out.println(String.format("Thread [%s] get lock obj2", Thread.currentThread().getName()));
                    }
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("Thread [%s] end...", Thread.currentThread().getName()));
        }
    }

    static class Thread2 extends Thread {

        @Override
        public void run() {
            System.out.println(String.format("Thread [%s] running...", Thread.currentThread().getName()));
            try {
                synchronized (obj2) {
                    System.out.println(String.format("Thread [%s] get lock obj2", Thread.currentThread().getName()));
                    Thread.sleep(2000);

                    synchronized (obj1) {
                        System.out.println(String.format("Thread [%s] get lock obj1", Thread.currentThread().getName()));
                    }
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("Thread [%s] end...", Thread.currentThread().getName()));
        }
    }
}

