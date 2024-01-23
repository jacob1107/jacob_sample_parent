package com.jacob.flowable.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author jacob
 */
@RestController
@RequestMapping("/index")
public class AindexController {


    @RequestMapping("/uuid")
    public String list(String userId) {
        return UUID.randomUUID().toString();
    }


}