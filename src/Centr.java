import java.util.concurrent.ArrayBlockingQueue;

public class Centr {

    final static int INTERVAL_BETWEEN_CALLS = 1000;
    final static int TALK = 3000;
    final static int NUMBER_OF_OPERATORS = 6;
    final static int ONE = 1;
    final static int CELLS = 21;
    final static ArrayBlockingQueue<Integer> calls = new ArrayBlockingQueue(20);

    public static void main(String[] args) throws InterruptedException {

        Thread ats = new Ats();
        ats.start();
        for (int x = ONE; x < NUMBER_OF_OPERATORS; x++) {
            int finalX = x;
            new Thread(() -> {
                while (true) {
                    try {
                        Integer i = calls.take();
                        System.out.println("Оператор " + finalX + " принял звонок номер " + i);
                        Thread.sleep(TALK);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        ;
        ats.join();
        System.out.println("Входящих звонков нет.");
    }

    static class Ats extends Thread {
        @Override
        public void run() {
            for (int i = ONE; i < CELLS; i++) {
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
}
// Я использовала ArrayBlockingQueue потому что звонки callcentr должны идти в очереди, а Операторы могут брать эти звонки
// по мере того как будут свободны. Очередь ArrayBlockingQueue - синхронизированая коллекция, поэтому Потоки синхронизируются
// автоматически. Звонки - ячеки, операторы могут взаимодействовать с ячейкой только если она свободна и там нет другого оператора!








