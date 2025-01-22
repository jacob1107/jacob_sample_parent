package org.jacob.spring.docker;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

    @RequestMapping(path = "info")
    public String hello() {
        return StringUtils.defaultString(System.getenv("env-info"), "uuid")
                + "==========" + UUID.randomUUID().toString();
    }

}
