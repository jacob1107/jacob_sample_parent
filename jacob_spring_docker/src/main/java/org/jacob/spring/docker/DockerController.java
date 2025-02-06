package org.jacob.spring.docker;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {
    public static final String OS_ARCH = "os.arch";
    public static final String OS_NAME = "os.name";
    public static final String ENV_INFO = "env-info";

    @RequestMapping(path = "info")
    public String hello() {

        String os_arch = StringUtils.defaultString(System.getProperty(OS_ARCH), OS_ARCH);
        String os_name = StringUtils.defaultString(System.getProperty("os.name"), OS_NAME);
        String env_info = StringUtils.defaultString(System.getenv("env-info"), ENV_INFO);
        StringBuffer stringBuffer = new StringBuffer();
        String mark = "\r\n=====";
        String mars = "====>";
        stringBuffer.append(mark).append(OS_ARCH).append(mars).append(os_arch).append(mark)
                .append(OS_NAME).append(mars).append(os_name).append(mark)
                .append(ENV_INFO).append(mars).append(env_info).append(mark)
                .append("uuid").append(mars).append(UUID.randomUUID().toString());
        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }

}
