// 代码生成时间: 2025-09-16 12:48:52
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内存使用情况分析工具
 *
 * 该类提供了分析Java程序内存使用情况的功能。
 * 它使用Java内置的ManagementFactory来获取内存相关信息，并打印出内存使用情况。
 */
public class MemoryUsageAnalysis {

    /**
     * 获取并打印内存使用情况
     */
    public void analyzeMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();

        System.out.println("Total Memory: " + memoryMXBean.getHeapMemoryUsage().getUsed() +
                " bytes, Free Memory: " + memoryMXBean.getHeapMemoryUsage().getCommitted() -
                memoryMXBean.getHeapMemoryUsage().getUsed() + " bytes");

        // 打印各个内存池的使用情况
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            MemoryUsage usage = memoryPoolMXBean.getUsage();
            System.out.println("Memory Pool: " + memoryPoolMXBean.getName());
            System.out.println("  Initial Size: " + usage.getInit() + " bytes");
            System.out.println("   Used: " + usage.getUsed() + " bytes");
            System.out.println("  Committed: " + usage.getCommitted() + " bytes");
            System.out.println("  Max: " + usage.getMax() + " bytes");
        }
    }

    /**
     * 程序入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        MemoryUsageAnalysis analysis = new MemoryUsageAnalysis();
        try {
            analysis.analyzeMemoryUsage();
        } catch (Exception e) {
            // 错误处理
            System.err.println("Error analyzing memory usage: " + e.getMessage());
        }
    }
}