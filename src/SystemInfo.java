import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class SystemInfo {
    public static void main(String[] args) {
        getVirtualMemoryInfo();
        System.out.println();
        getProcessorInfo();
        System.out.println();
        getFreeMemoryInfo();
    }

    public static void getVirtualMemoryInfo() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalMemory = osBean.getTotalPhysicalMemorySize();
        long freeMemory = osBean.getFreePhysicalMemorySize();
        long usedMemory = totalMemory - freeMemory;
        double usedMemoryPercent = (double) usedMemory / totalMemory * 100;

        System.out.println("=== Информация о параметрах виртуальной памяти ===");
        System.out.println("Общий объем памяти: " + totalMemory + " байт");
        System.out.println("Доступный объем памяти: " + freeMemory + " байт");
        System.out.println("Использованная память: " + usedMemory + " байт");
        System.out.println("Процент использования памяти: " + usedMemoryPercent + "%");
    }

    public static void getProcessorInfo() {
        String processorModel = System.getProperty("os.arch");
        System.out.println("** Информация о процессоре **");
        System.out.println("Модель процессора: " + processorModel);
    }

    public static void getFreeMemoryInfo() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("-- Информация о свободной памяти процесса --");
        System.out.println("Физическая память: " + nonHeapMemoryUsage.getCommitted() + " байт");
        System.out.println("Виртуальная память: " + heapMemoryUsage.getCommitted() + " байт");
        // Getting the number of regions is not straightforward in Java
        // You might need to use a different approach to get this information
    }
}
