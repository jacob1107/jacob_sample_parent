package org.jacob.spring.docker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.ApplicationAvailability;
import sun.rmi.runtime.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Hello world!
 */
@SpringBootApplication
public class DockerApp {
    private static final Logger log = LoggerFactory.getLogger(DockerApp.class);

    public static void main(String[] args) {
        /*
         * System.setProperty("KUBERNETES_SERVICE_HOST", "10.96.0.1");
         * System.setProperty("KUBERNETES_SERVICE_PORT", "443");
         */
        /*		System.setProperty("SERVICE_HOST", "10.96.0.1");*/
        /* System.setProperty("env-info", "jacob");*/
        Properties properties = System.getProperties();

        Iterator it = properties.entrySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            log.info("key :{}, value :{}", key, properties.get(key));
        }
        SpringApplication.run(DockerApp.class, args);
    }
}
