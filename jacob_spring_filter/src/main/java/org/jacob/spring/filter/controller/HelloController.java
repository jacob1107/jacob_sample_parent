package org.jacob.spring.filter.controller;

import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @PostMapping("/hello")
    @ResponseBody
    public Map hello(@RequestParam Map map) {
        log.info("req=============={}", JSON.toJSONString(map));
        map.put("uuid", UUID.randomUUID().toString());
        return map;
    }
}
