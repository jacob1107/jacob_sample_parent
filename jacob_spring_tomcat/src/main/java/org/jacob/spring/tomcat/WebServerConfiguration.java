package org.jacob.spring.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

//当Spring容器中没有TomcatEmbeddedServletContainerFactory这个bean时，会把此bean加载进容器
@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //使用工厂类定制tomcat connector
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocol= (Http11NioProtocol) connector.getProtocolHandler();
                //设置30秒无响应则断开keepalive
                protocol.setKeepAliveTimeout(1000);
                //设置最大10000次请求后则断开keepalive
                protocol.setMaxKeepAliveRequests(1000);
            }
        });
    }
}