package com.jacob.k8s.lws.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PodInfoService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(PodInfoService.class);

    @Value("${POD_NAME:unknown}")
    private String podName;

    @Value("${POD_NAMESPACE:default}")
    private String namespace;

    @Value("${HOST_IP:unknown}")
    private String hostIp;

    @Value("${POD_IP:unknown}")
    private String podIp;

    private String hostname;
    private final Map<String, Object> podInfo = new ConcurrentHashMap<>();
    private final Map<String, String> clusterNodes = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            this.hostname = "unknown";
        }

        // 收集 Pod 信息
        podInfo.put("podName", podName);
        podInfo.put("namespace", namespace);
        podInfo.put("hostIp", hostIp);
        podInfo.put("podIp", podIp);
        podInfo.put("hostname", hostname);

        // 判断是否为领导者 Pod
        boolean isLeaderPod = podName.endsWith("-0");
        podInfo.put("isLeaderPod", isLeaderPod);
        podInfo.put("role", isLeaderPod ? "leader" : "worker");

        logger.info("Pod Info: {}", podInfo);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application context refreshed. Current pod: {}, IP: {}, Host: {}", podName, podIp, hostname);
    }

    public Map<String, Object> getPodInfo() {
        return new HashMap<>(podInfo);
    }

    public String getPodName() {
        return podName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPodIp() {
        return podIp;
    }

    public String getHostname() {
        return hostname;
    }

    public boolean isLeaderPod() {
        return podName.endsWith("-0");
    }

    public void addClusterNode(String nodeId, String nodeInfo) {
        clusterNodes.put(nodeId, nodeInfo);
    }

    public void removeClusterNode(String nodeId) {
        clusterNodes.remove(nodeId);
    }

    public Map<String, String> getClusterNodes() {
        return new HashMap<>(clusterNodes);
    }
}