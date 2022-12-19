package com.jacob.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author jacob
 * @Date 2022/11/28 16:32
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class HelloControllerTest {


    @Test
    void hello() {
        Assert.assertEquals("1", "1");
    }
}