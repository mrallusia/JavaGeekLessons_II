import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Race {
    private ArrayList<Stage> stages;

    public ArrayList<Stage> getStages() { return stages; }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public void startRace(Car[] cars) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // задаем значения счетчиков по количеству машин в гонке
        final CyclicBarrier cb = new CyclicBarrier(cars.length);
        final Semaphore smp = new Semaphore(cars.length/2);
        final CountDownLatch cdl = new CountDownLatch(cars.length);
        final CountDownLatch cdl2 = new CountDownLatch(cars.length);
        final Lock lock = new ReentrantLock(); // для победителя


        for (int i = 0; i < cars.length; i++) {
            final int w = i;
            executorService.execute(() -> {

                // подготовка машин к гонке
                cars[w].carPrepare();
                cdl.countDown();

                // приостановка чтобы все машины были в однйо точке
                try {
                    cb.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Участок с тонелем, симофор ! Не уверен, что он тут корректно срабатывает.
                try {
                    for (int j = 0; j < stages.size(); j++) {
                        if (getStages().get(j).getDescription().startsWith("Тоннель ")) {
                            smp.acquire();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    smp.release();
                }

                cars[w].run();
                cdl2.countDown();

                // Победитель
                try {
                    if (lock.tryLock(5, TimeUnit.SECONDS)) {
                        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Победитель гонки: " + cars[w].getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        executorService.shutdown();

        try {
            cdl.await();
            // пока все не будут готовый гонка не начнётся
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            cdl2.await();
            // пока все не доедут гонка не закончится
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            //lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}