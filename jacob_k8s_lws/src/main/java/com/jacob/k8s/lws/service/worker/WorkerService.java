package com.jacob.k8s.lws.service.worker;

import com.jacob.k8s.lws.service.PodInfoService;
import com.jacob.k8s.lws.service.leader.LeaderElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerService.class);

    @Value("${POD_NAME:unknown}")
    private String podName;

    @Autowired
    private LeaderElectionService leaderElectionService;

    @Autowired
    private PodInfoService podInfoService;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    private final Map<String, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicInteger taskCounter = new AtomicInteger(0);

    private static class Task {
        String id;
        String name;
        String status; // PENDING, RUNNING, COMPLETED, FAILED
        String assignedTo;
        LocalDateTime startTime;
        LocalDateTime endTime;
        String result;

        Task(String id, String name, String assignedTo) {
            this.id = id;
            this.name = name;
            this.status = "PENDING";
            this.assignedTo = assignedTo;
            this.startTime = LocalDateTime.now();
        }
    }

    @PostConstruct
    public void init() {
        logger.info("WorkerService initialized for pod: {}", podName);

        // 启动任务调度器
        executor.scheduleAtFixedRate(this::checkForTasks, 5, 5, TimeUnit.SECONDS);

        // 定期清理已完成的任务
        executor.scheduleAtFixedRate(this::cleanupOldTasks, 1, 1, TimeUnit.MINUTES);
    }

    // 只有领导者才能创建新任务
    @Scheduled(fixedDelay = 10000)
    public void scheduleNewTasks() {
        if (leaderElectionService.isLeader()) {
            int newTasks = 3;
            for (int i = 0; i < newTasks; i++) {
                String taskId = "task-" + System.currentTimeMillis() + "-" + i;
                Task task = new Task(taskId, "Generated Task " + i, "worker-" + (i % 2 + 1));
                task.status = "PENDING";
                tasks.put(taskId, task);
                logger.info("Leader scheduled new task: {} for {}", taskId, task.assignedTo);
            }
        }
    }

    // 工作者处理任务
    private void checkForTasks() {
        if (!leaderElectionService.isLeader()) {
            // 工作者只处理分配给自己的任务
            String workerId = "worker-" + podName.substring(podName.lastIndexOf('-') + 1);

            tasks.values().stream().filter(task -> "PENDING".equals(task.status) && workerId.equals(task.assignedTo)).forEach(task -> {
                logger.info("Worker {} processing task: {}", workerId, task.id);
                task.status = "RUNNING";

                // 异步处理任务
                executor.submit(() -> processTask(task));
            });
        }
    }

    private void processTask(Task task) {
        try {
            // 模拟任务处理
            Thread.sleep(new Random().nextInt(3000) + 1000);

            task.status = "COMPLETED";
            task.endTime = LocalDateTime.now();
            task.result = "Task completed successfully at " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(task.endTime);

            logger.info("Task {} completed by {}", task.id, podName);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            task.status = "FAILED";
            task.result = "Task interrupted";
        } catch (Exception e) {
            logger.error("Error processing task: {}", task.id, e);
            task.status = "FAILED";
            task.result = "Error: " + e.getMessage();
        }
    }

    // 清理旧任务
    private void cleanupOldTasks() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);

        tasks.entrySet().removeIf(entry -> {
            Task task = entry.getValue();
            if (("COMPLETED".equals(task.status) || "FAILED".equals(task.status)) && task.endTime != null && task.endTime.isBefore(cutoff)) {
                logger.debug("Cleaning up old task: {}", task.id);
                return true;
            }
            return false;
        });
    }

    // 获取任务统计
    public Map<String, Object> getTaskStatistics() {
        Map<String, Object> stats = new HashMap<>();

        long pending = tasks.values().stream().filter(t -> "PENDING".equals(t.status)).count();
        long running = tasks.values().stream().filter(t -> "RUNNING".equals(t.status)).count();
        long completed = tasks.values().stream().filter(t -> "COMPLETED".equals(t.status)).count();
        long failed = tasks.values().stream().filter(t -> "FAILED".equals(t.status)).count();

        stats.put("totalTasks", tasks.size());
        stats.put("pendingTasks", pending);
        stats.put("runningTasks", running);
        stats.put("completedTasks", completed);
        stats.put("failedTasks", failed);
        stats.put("podName", podName);
        stats.put("isLeader", leaderElectionService.isLeader());

        return stats;
    }

    // 获取所有任务
    public List<Map<String, Object>> getAllTasks() {
        List<Map<String, Object>> taskList = new ArrayList<>();

        tasks.values().forEach(task -> {
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("id", task.id);
            taskMap.put("name", task.name);
            taskMap.put("status", task.status);
            taskMap.put("assignedTo", task.assignedTo);
            taskMap.put("startTime", task.startTime);
            taskMap.put("endTime", task.endTime);
            taskMap.put("result", task.result);
            taskList.add(taskMap);
        });

        return taskList;
    }

    // 添加新任务（由领导者调用）
    public boolean addTask(String taskName, String assignedTo) {
        if (!leaderElectionService.isLeader()) {
            return false;
        }

        String taskId = "manual-task-" + System.currentTimeMillis();
        Task task = new Task(taskId, taskName, assignedTo);
        tasks.put(taskId, task);

        logger.info("Manually added task: {} for {}", taskName, assignedTo);
        return true;
    }

    // 获取活跃工作者数量
    public int getActiveWorkerCount() {
        return 2; // 在 LeaderWorkerSet 中，有 2 个工作者
    }
}