package com.jacob.k8s.lws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jacob.k8s.lws.service.leader.LeaderElectionService;
import com.jacob.k8s.lws.service.PodInfoService;
import com.jacob.k8s.lws.service.worker.WorkerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthController {
    private final long startTime = System.currentTimeMillis();
    @Autowired
    private LeaderElectionService leaderElectionService;

    @Autowired
    private PodInfoService podInfoService;

    @Autowired
    private WorkerService workerService;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.put("podInfo", podInfoService.getPodInfo());
        response.put("isLeader", leaderElectionService.isLeader());
        response.put("leaderInfo", leaderElectionService.getLeaderInfo());
        return response;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Leader-Worker Demo");
        response.put("version", "1.0.0");
        response.put("springBootVersion", "2.3.12.RELEASE");
        response.put("javaVersion", "1.8");
        response.put("podInfo", podInfoService.getPodInfo());
        return response;
    }

    @GetMapping("/leader/status")
    public Map<String, Object> leaderStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("isLeader", leaderElectionService.isLeader());
        response.put("podName", podInfoService.getPodName());
        response.put("podIp", podInfoService.getPodIp());
        response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.put("leaderPod", podInfoService.isLeaderPod() ? "Yes" : "No");
        return response;
    }

    @GetMapping("/tasks/stats")
    public Map<String, Object> taskStatistics() {
        return workerService.getTaskStatistics();
    }

    @GetMapping("/tasks")
    public Map<String, Object> getAllTasks() {
        Map<String, Object> response = new HashMap<>();
        response.put("tasks", workerService.getAllTasks());
        response.put("count", workerService.getAllTasks().size());
        response.put("pod", podInfoService.getPodName());
        return response;
    }

    @PostMapping("/tasks")
    public Map<String, Object> createTask(@RequestParam String taskName, @RequestParam(required = false, defaultValue = "worker-1") String assignedTo) {

        Map<String, Object> response = new HashMap<>();

        if (leaderElectionService.isLeader()) {
            boolean success = workerService.addTask(taskName, assignedTo);
            response.put("success", success);
            response.put("message", success ? "Task created successfully" : "Failed to create task");
            response.put("assignedTo", assignedTo);
        } else {
            response.put("success", false);
            response.put("message", "Only leader can create tasks");
            response.put("currentRole", "worker");
        }

        return response;
    }

    @GetMapping("/cluster")
    public Map<String, Object> clusterInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("podInfo", podInfoService.getPodInfo());
        response.put("clusterNodes", podInfoService.getClusterNodes());
        response.put("totalNodes", podInfoService.getClusterNodes().size() + 1);
        response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return response;
    }

    @GetMapping("/metrics")
    public Map<String, Object> metrics() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> jvm = new HashMap<>();

        Runtime runtime = Runtime.getRuntime();
        jvm.put("freeMemory", runtime.freeMemory());
        jvm.put("totalMemory", runtime.totalMemory());
        jvm.put("maxMemory", runtime.maxMemory());
        jvm.put("availableProcessors", runtime.availableProcessors());

        response.put("jvm", jvm);
        response.put("taskStats", workerService.getTaskStatistics());
        response.put("uptime", System.currentTimeMillis() - startTime);
        return response;
    }


}