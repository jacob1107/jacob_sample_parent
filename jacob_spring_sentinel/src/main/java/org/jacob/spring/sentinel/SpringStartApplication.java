package org.jacob.spring.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;


@SpringBootApplication
public class SpringStartApplication {
	public static void main(String[] args) {
		ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
		SpringApplication.run(SpringStartApplication.class, args);
	}
}
