
package org.jacob.spring.httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSON;

public class HttpClient {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 700; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					httpClient();

				}
			}).start();

		}
	}

	private static void httpClient() {
		// getString();
		post();

	}

	/**
	 * connectionRequestTimout：指从连接池获取连接的timeout
	 * connetionTimeout：指客户端和服务器建立连接的timeout，
	 * 就是http请求的三个阶段，一：建立连接；二：数据传送；三，断开连接。超时后会ConnectionTimeOutException
	 * socketTimeout：指客户端从服务器读取数据的timeout，超出后会抛出SocketTimeOutException
	 */
	protected static void getString() {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestConfig requestConfig = setrequestConfig();
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8081/helloworld3");
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;

		try {
			response = httpclient.execute(httpGet);
			System.out.println(response.getStatusLine());
			HttpEntity entity1 = response.getEntity();
			System.err.println(EntityUtils.toString(entity1));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	protected static RequestConfig setrequestConfig() {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000)
				.setConnectionRequestTimeout(1000).build();
		return requestConfig;
	}

	protected static void getString2() {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		RequestConfig requestConfig = setrequestConfig();
		CloseableHttpResponse response = null;
		try {
			URIBuilder uriBuilder = new URIBuilder("http://127.0.0.1:8081/helloworld3");
			// 第一种
			uriBuilder.addParameter("name", "name");
			uriBuilder.addParameter("password", "password");

			// 第二种
			List<NameValuePair> nvps = new ArrayList<>();
			NameValuePair nameValuePair = new BasicNameValuePair("name", "name");
			NameValuePair nameValuePair2 = new BasicNameValuePair("password", "password");
			nvps.add(nameValuePair2);
			nvps.add(nameValuePair);
			uriBuilder.addParameters(nvps);

			HttpGet httpGet = new HttpGet(uriBuilder.build());
			httpGet.setConfig(requestConfig);
			response = httpclient.execute(httpGet);
			System.out.println(response.getStatusLine());
			HttpEntity entity1 = response.getEntity();
			System.err.println(EntityUtils.toString(entity1));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	protected static void post() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// RequestConfig requestConfig = setrequestConfig();
		HttpPost httpPost = new HttpPost("http://127.0.0.1:8081/say");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("servName", "app"));
		nvps.add(new BasicNameValuePair("name", "age"));
		nvps.add(new BasicNameValuePair("sex", "sex"));
		nvps.add(new BasicNameValuePair("age", "1"));
		CloseableHttpResponse response = null;
		try {
			// httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpclient.execute(httpPost);
			System.out.println(response.getStatusLine());
			HttpEntity entity2 = response.getEntity();
			System.err.println(EntityUtils.toString(entity2));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected static void postJson() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = setrequestConfig();

		HttpPost httpPost = new HttpPost("http://127.0.0.1:8081/helloworld3");
		Map<String, String> map = new HashMap<>();
		map.put("username", "vip");
		map.put("password", "secret");

		String jsonString = JSON.toJSONString(map);
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(jsonString);
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			httpPost.setConfig(requestConfig);
			response = httpclient.execute(httpPost);
			System.out.println(response.getStatusLine());
			HttpEntity entity2 = response.getEntity();
			System.err.println(EntityUtils.toString(entity2));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}