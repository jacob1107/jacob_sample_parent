package org.jacob.spring.sentinel.cluster.server.alone;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author houyi
 * @date 2019-01-23
 **/
public class ClusterServer {

	private static final String RULEKEY = "flow-rules";

	private static final int CLUSTER_SERVER_PORT = 11111;

	private static final String NAMESPACENAME = "application";

	/**
	 * 初始化集群限流的Supplier 这样如果后期集群限流的规则发生变更的话，系统可以自动感知到
	 */
	private void initClusterFlowSupplier() {
		// 为集群流控注册一个Supplier，该Supplier会根据namespace动态创建数据源
		ClusterFlowRuleManager.setPropertySupplier(namespace -> {
			// 使用 Nacos 数据源作为配置中心，需要在 REMOTE_ADDRESS 上启动一个 Nacos 的服务
			ReadableDataSource<String, List<FlowRule>> ds = new ApolloDataSource<>(NAMESPACENAME, RULEKEY, namespace,
					source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
					}));
			System.err.println(ds.getProperty());
			return ds.getProperty();
		});
	}

	/**
	 * 初始化集群热点参数限流的Supplier 这样如果后期集群热点参数限流的规则发生变更的话，系统可以自动感知到
	 */
	public void initClusterParamFlowSupplier() {
		// 为集群热点参数流控注册一个Supplier，该Supplier会根据namespace动态创建数据源
		ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
			// 使用 Nacos 数据源作为配置中心，需要在 REMOTE_ADDRESS 上启动一个 Nacos 的服务
			ReadableDataSource<String, List<ParamFlowRule>> ds = new ApolloDataSource<>(NAMESPACENAME, RULEKEY,
					namespace, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
					}));
			System.err.println(ds.getProperty());
			return ds.getProperty();
		});
	}

	/**
	 * 为Namespace的集合注册一个SentinelProperty 这样如果后期namespace集合发生变更的话，系统可以自动感知到
	 */
	private void registerNamespaceProperty() {
		String namespaceSetDataId = "cluster-server-namespace-set";
		// 初始化一个配置 namespace 的 Nacos 数据源
		ReadableDataSource<String, Set<String>> namespaceDs = new ApolloDataSource<>(NAMESPACENAME, RULEKEY,
				namespaceSetDataId, source -> JSON.parseObject(source, new TypeReference<Set<String>>() {
				}));
		ClusterServerConfigManager.registerNamespaceSetProperty(namespaceDs.getProperty());
	}

	/**
	 * 为ServerTransportConfig注册一个SentinelProperty 这样的话可以动态的更改这些配置
	 */
	private void registerServerTransportProperty() {
		String serverTransportDataId = "cluster-server-transport-config";
		// 初始化一个配置服务端通道配置的 Nacos 数据源
		ReadableDataSource<String, ServerTransportConfig> transportConfigDs = new ApolloDataSource<>(NAMESPACENAME,
				RULEKEY, serverTransportDataId,
				source -> JSON.parseObject(source, new TypeReference<ServerTransportConfig>() {
				}));
		ClusterServerConfigManager.registerServerTransportProperty(transportConfigDs.getProperty());
	}

	/**
	 * 加载namespace的集合以及ServerTransportConfig
	 * 最好还要再为他们每个都注册一个SentinelProperty，这样的话可以动态的修改这些配置项
	 */
	private void loadServerConfig() {
		// 加载namespace
		ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(NAMESPACENAME));
		// 加载ServerTransportConfig
		ClusterServerConfigManager.loadGlobalTransportConfig(
				new ServerTransportConfig().setIdleSeconds(600).setPort(CLUSTER_SERVER_PORT));
	}

	/**
	 * 初始化工作
	 */
	public void init() {
		// 初始化集群限流的规则
		initClusterFlowSupplier();
		// 加载服务端的配置
		loadServerConfig();
	}

	/**
	 * 注册Property
	 */
	public void registerProperty() {
		// 注册namespace的SentinelProperty
		registerNamespaceProperty();
		// 注册ServerTransportConfig的SentinelProperty
		registerServerTransportProperty();
	}

	/**
	 * 启动ClusterToken服务端
	 */
	public void start() throws Exception {
		// 创建一个 ClusterTokenServer 的实例，独立模式
		ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();
		// 启动
		tokenServer.start();
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("project.name", "sentinel");
		System.setProperty("csp.sentinel.dashboard.server", "127.0.0.1:8089");
		System.setProperty("csp.sentinel.log.use.pid", "true");
		ClusterServer clusterServer = new ClusterServer();
		clusterServer.init();
		clusterServer.start();
	}

}
