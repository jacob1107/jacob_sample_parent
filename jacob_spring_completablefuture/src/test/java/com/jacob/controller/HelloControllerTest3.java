package com.jacob.controller;

import javafx.scene.chart.Chart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.nio.charset.Charset;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class HelloControllerTest3 {
    @Autowired
    private MockMvc mockMvc;
    private static final Logger logger = LoggerFactory.getLogger(HelloControllerTest3.class);

    @Before
    public void setUp() throws Exception {
        System.out.println("================start================");
    }

    @After
    public void setDown() throws Exception {
        System.out.println("================end==================");

    }


    @Test
    @Transactional
    @Rollback()
    public void save() throws Exception {
        String json = "";
        //执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
        mockMvc.perform(MockMvcRequestBuilders
                .post("/hello3")
                //传json参数
                .content(json.getBytes())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer ********-****-****-****-************")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void get() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/get")
                .param("id", "**********")
                .header("Authorization", "Bearer ********-****-****-****-************")
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void upd() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/hello3")
                .param("id", "**********")
                .header("Authorization", "Bearer ********-****-****-****-************")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")

        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        String contentAsString = resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.defaultCharset()); //防止中文乱码;
        logger.info("打印结果=====>>> {}", contentAsString);
    }
}
