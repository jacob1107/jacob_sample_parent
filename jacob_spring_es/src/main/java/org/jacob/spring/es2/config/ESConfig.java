package org.jacob.spring.es2.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
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

	@Bean
	public RestHighLevelClient elasticsearchClient() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port)).setHttpClientConfigCallback(
				httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

		return new RestHighLevelClient(builder);
	}

	@Bean
	public ElasticsearchRestTemplate elasticsearchTemplate() {
		return new ElasticsearchRestTemplate(elasticsearchClient());
	}


	// http://192.168.17.128:30533/
	/*
	 * @Bean public RestClient getClient() throws KeyStoreException,
	 * NoSuchAlgorithmException, KeyManagementException { //
	 * 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议 RestClientBuilder
	 * clientBuilder = RestClient.builder(new HttpHost("192.168.17.128", 30533,
	 * "http"));
	 * 
	 * // 最后配置好的clientBuilder再build一下即可得到真正的Client return clientBuilder.build(); }
	 */
}
