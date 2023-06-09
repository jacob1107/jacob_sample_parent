package org.jacob.spring.tomcat;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@RestController
@EnableAutoConfiguration
public class HelloWorldController {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
    private int times = 0;

    @RequestMapping("/hello")
    public String sayHello() {
        times++;
        String result = times + "==>Really appreciate your star, that's the power of our life.";
        logger.info(result);
        return result;
    }
}
