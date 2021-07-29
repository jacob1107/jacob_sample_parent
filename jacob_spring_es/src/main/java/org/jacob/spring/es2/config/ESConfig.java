package org.jacob.spring.es2.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
public class ESConfig {

	@Value("${elastic.host}")
	private String host;

	@Value("${elastic.port}")
	private int port;

	@Value("${elastic.username}")
	private String userName;

	@Value("${elastic.password}")
	private String password;

	/**
	 * 高级客户端，带密码验证
	 * 
	 * @return
	 */
	//@Bean
	public RestHighLevelClient elasticsearchClient2() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "https")).setHttpClientConfigCallback(
				httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

		return new RestHighLevelClient(builder);
	}

	//@Bean
	public RestHighLevelClient elasticsearchClient3() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "https")).setHttpClientConfigCallback(
				httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

		// 认证和线程数
		builder.setHttpClientConfigCallback(httpClientBuilder -> {
			httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			int threadCount = 10;
			httpClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(threadCount).build());

			return httpClientBuilder;
		});

		// 超时超时设置
		builder.setRequestConfigCallback(requestConfigCallback -> {
			requestConfigCallback.setConnectTimeout(10);
			requestConfigCallback.setSocketTimeout(10);
			return requestConfigCallback;
		});

		return new RestHighLevelClient(builder);
	}

	/**
	 * 高级客户端，不带密码验证
	 * 
	 * @return
	 */
	@Bean
	public RestHighLevelClient elasticsearchClient() {

		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "http"));

		return new RestHighLevelClient(builder);
	}

	@Bean
	public ElasticsearchRestTemplate elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(elasticsearchClient());
	}

	/**
	 * 低级客户端
	 * 
	 * @return
	 */
	//@Bean
	public RestClient getClient() {
		// 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议
		RestClient clientBuilder = RestClient.builder(new HttpHost("192.168.17.128", 30533, "http")).build();
		return clientBuilder;

	}

}
