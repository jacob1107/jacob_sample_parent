package org.jacob.springcloud.gateway.services.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GateWayConfig {

	private static final Logger log = LoggerFactory.getLogger(GateWayConfig.class);

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		log.info("信息初始化完成==============================");
		RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

		routes.route("path_route_atguigu", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();

		routes.route("a0", fn -> fn.method(HttpMethod.POST).and().path("/a").uri("http://127.0.0.1:19526")).build();
		routes.route("a1", fn -> fn.method(HttpMethod.POST).or().method(HttpMethod.GET).and().path("/a").uri("http://127.0.0.1:19526")).build();

		return routes.build();
	}
}
