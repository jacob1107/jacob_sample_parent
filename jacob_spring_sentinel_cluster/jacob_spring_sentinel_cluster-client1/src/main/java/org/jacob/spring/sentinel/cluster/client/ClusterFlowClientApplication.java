package org.jacob.spring.sentinel.cluster.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动时加参数： -Dproject.name=xxx -Dcsp.sentinel.dashboard.server=consoleIp:port
 * 可以在对应的 sentinel 的 dashboard 中查看效果
 */

@SpringBootApplication
public class ClusterFlowClientApplication {
	public static void main(String[] args) {

		// System.setProperty("project.name", "sentinel-clinet");
		System.setProperty("project.name", "sentinel");
		System.setProperty("csp.sentinel.log.use.pid", "true");
		System.setProperty("csp.sentinel.dashboard.server", "127.0.0.1:8089");
		SpringApplication.run(ClusterFlowClientApplication.class, args);
	}
}
