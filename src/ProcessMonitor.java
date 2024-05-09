import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class ProcessMonitor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Задание 1: Список процессов и получение информации о процессах
        System.out.println("Список процессов:");
        List<ProcessHandle> processes = ProcessHandle.allProcesses().toList();
        for (ProcessHandle process : processes) {
            ProcessHandle.Info info = process.info();
            System.out.println("PID: " + process.pid() + ", Имя: " + info.command().orElse("Unknown"));
        }

        System.out.print("Введите идентификатор процесса: ");
        long pid = scanner.nextLong();
        ProcessHandle process = ProcessHandle.of(pid).orElse(null);
        if (process != null) {
            ProcessHandle.Info info = process.info();
            System.out.println("Количество потоков: " + info.totalCpuDuration().orElse(null));
        } else {
            System.out.println("Процесс не найден.");
        }

        // Получение информации о загрузке ЦП для каждого ядра
        System.out.println("\nЗагрузка каждого ядра процессора:");
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double systemLoadAverage = osBean.getSystemLoadAverage();
        int availableProcessors = osBean.getAvailableProcessors();
        System.out.println("Загрузка процессора: " + (systemLoadAverage / availableProcessors * 100) + "%");

        // Задание 2: Загрузка процессора каждым процессом
        osBean = ManagementFactory.getOperatingSystemMXBean();
        for (ProcessHandle proc : processes) {
            ProcessHandle.Info processInfo = proc.info(); // Переименованная переменная
            long pidOfProc = proc.pid();
            Double processCpuLoad = processInfo.totalCpuDuration()
                    .map(duration -> (double) duration.toNanos() / 1.0E9) // Исправленный участок кода
                    .map(value -> (value / processInfo.totalCpuDuration().orElse(Duration.ZERO).toNanos()))
                    .orElse(0.0);
            System.out.println("PID: " + pidOfProc + ", Загрузка: " + processCpuLoad + "%");
        }

        scanner.close();
    }
}