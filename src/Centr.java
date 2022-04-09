import java.util.concurrent.ArrayBlockingQueue;

public class Centr {

    final static int INTERVAL_BETWEEN_CALLS = 1000;
    final static int TALK = 3000;
    final static ArrayBlockingQueue<Integer> calls = new ArrayBlockingQueue(20);

    public static void main(String[] args) throws InterruptedException {

        Thread ats = new Ats();
        Thread operator = new Operator();
        ats.start();
        operator.start();
        ats.join();
        operator.join();
        System.out.println("Входящих звонков нет.");
    }

    static class Ats extends Thread {
        @Override
        public void run() {
            for (int i = 1; i < 21; i++) {
                try {
                    calls.put(i);
                    System.out.println("Входящий звонок " + " номер " + i);
                    Thread.sleep(INTERVAL_BETWEEN_CALLS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Operator extends Thread {
        @Override
        public void run() {
            for (int y = 1; y < 6; y++) {
                int finalY = y;
                new Thread(() -> {
                    while (true) {
                        try {
                            Integer i = calls.take();
                            System.out.println("Оператор " + finalY + " принял звонок номер " + i);
                            Thread.sleep(TALK);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        }
    }
}






