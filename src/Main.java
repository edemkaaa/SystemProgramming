import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CountDownLatch;

class Main {
    // Использование блокировки (Lock)
    private static Lock lock = new ReentrantLock();
    private static int sharedResourceLock = 0;

    // Использование мьютекса (ReentrantLock)
    private static Lock mutex = new ReentrantLock();
    private static int sharedResourceMutex = 0;

    // Использование семафора (Semaphore)
    private static Semaphore semaphore = new Semaphore(1);
    private static int sharedResourceSemaphore = 0;

    // Использование событий (CountDownLatch)
    private static CountDownLatch latch = new CountDownLatch(1);
    private static int sharedResourceLatch = 0;

    public static void main(String[] args) throws InterruptedException {
        // Создаем потоки
        Thread[] threadsLock = new Thread[10];
        Thread[] threadsMutex = new Thread[10];
        Thread[] threadsSemaphore = new Thread[10];
        Thread[] threadsLatch = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threadsLock[i] = new Thread(() -> incrementLock());
            threadsMutex[i] = new Thread(() -> incrementMutex());
            threadsSemaphore[i] = new Thread(() -> incrementSemaphore());
            threadsLatch[i] = new Thread(() -> incrementLatch());

            threadsLock[i].start();
            threadsMutex[i].start();
            threadsSemaphore[i].start();
            threadsLatch[i].start();
        }

        // Разрешаем событие
        latch.countDown();

        // Ожидаем завершения всех потоков
        for (Thread thread : threadsLock) {
            thread.join();
        }
        for (Thread thread : threadsMutex) {
            thread.join();
        }
        for (Thread thread : threadsSemaphore) {
            thread.join();
        }
        for (Thread thread : threadsLatch) {
            thread.join();
        }

        // Вывод результатов
        System.out.println("Результат с использованием блокировки: " + sharedResourceLock);
        System.out.println("Результат с использованием мьютекса: " + sharedResourceMutex);
        System.out.println("Результат с использованием семафора: " + sharedResourceSemaphore);
        System.out.println("Результат с использованием событий: " + sharedResourceLatch);
    }

    // Методы инкремента для каждого механизма синхронизации
    private static void incrementLock() {
        lock.lock();
        try {
            sharedResourceLock++;
        } finally {
            lock.unlock();
        }
    }

    private static void incrementMutex() {
        mutex.lock();
        try {
            sharedResourceMutex++;
        } finally {
            mutex.unlock();
        }
    }

    private static void incrementSemaphore() {
        try {
            semaphore.acquire();
            sharedResourceSemaphore++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private static void incrementLatch() {
        try {
            latch.await();
            sharedResourceLatch++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
