package com.jacob.k8s.lws.service.leader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.scheduling.annotation.Scheduled;

@Service
public class LeaderService {

    @Value("${HOSTNAME:unknown}")
    private String hostname;

    private final AtomicBoolean isLeader = new AtomicBoolean(false);
    private final AtomicReference<String> currentLeader = new AtomicReference<>("none");
    private long lastHeartbeat = System.currentTimeMillis();

    // 模拟领导者选举
    @PostConstruct
    public void init() {
        // 在实际生产中，这里应该使用Kubernetes Lease对象或分布式锁
        // 这里简化为第一个启动的实例成为领导者
        if (System.getenv("POD_NAME") != null &&
                System.getenv("POD_NAME").contains("leader")) {
            isLeader.set(true);
            currentLeader.set(hostname);
            System.out.println("I am the leader: " + hostname);
        } else {
            System.out.println("I am a worker: " + hostname);
        }
    }

    // 领导者心跳
    @Scheduled(fixedDelay = 5000)
    public void leaderHeartbeat() {
        if (isLeader.get()) {
            lastHeartbeat = System.currentTimeMillis();
            System.out.println("Leader heartbeat from: " + hostname);
        }
    }

    // 检查领导者状态
    @Scheduled(fixedDelay = 3000)
    public void checkLeadership() {
        // 模拟领导者故障转移
        if (isLeader.get() &&
                System.currentTimeMillis() - lastHeartbeat > 10000) {
            // 领导者失活，可以触发重新选举
            System.out.println("Leader inactive, should trigger election");
        }
    }

    public boolean isCurrentLeader() {
        return isLeader.get();
    }

    public String getCurrentLeaderId() {
        return currentLeader.get();
    }

    public void acquireLeadership() {
        isLeader.set(true);
        currentLeader.set(hostname);
    }

    public void releaseLeadership() {
        isLeader.set(false);
    }
}