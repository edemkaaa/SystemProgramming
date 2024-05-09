import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class ThreadPriorityExample {

    public static void main(String[] args) {
        // Получаем идентификатор текущего процесса
        long pid = ProcessHandle.current().pid();

        // Создаем и запускаем потоки с разными приоритетами
        Thread threadLowPriority = new Thread(() -> testFunction("Низкий приоритет"));
        Thread threadNormalPriority = new Thread(() -> testFunction("Нормальный приоритет"));
        Thread threadHighPriority = new Thread(() -> testFunction("Высокий приоритет"));

        threadLowPriority.setPriority(Thread.MIN_PRIORITY);
        threadNormalPriority.setPriority(Thread.NORM_PRIORITY);
        threadHighPriority.setPriority(Thread.MAX_PRIORITY);

        threadLowPriority.start();
        threadNormalPriority.start();
        threadHighPriority.start();

        // Ожидаем завершения потоков
        try {
            threadLowPriority.join();
            threadNormalPriority.join();
            threadHighPriority.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Получаем информацию о загрузке ЦП
        double cpuUsage = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
        System.out.println("Использование ЦП: " + cpuUsage + "%");

        // Получаем информацию о потоках приложения
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        int threadCount = threadMXBean.getThreadCount();
        System.out.println("Количество потоков в тестовом приложении: " + threadCount);
    }

    public static void testFunction(String priority) {
        // Основная работа потока
        for (int i = 0; i < 10; i++) {
            // Выводим информацию о текущем потоке и его приоритете
            System.out.println("Поток: " + Thread.currentThread().getName() + ", приоритет: " + priority + ", итерация: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
