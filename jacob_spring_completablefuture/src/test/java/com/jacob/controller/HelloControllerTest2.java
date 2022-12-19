package com.jacob.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class HelloControllerTest2 {
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        System.out.println("---------------start---------------");
        save();
        get();
        System.out.println("================end================");
    }


    @Test
    @Transactional
    @Rollback()
    public void save() throws Exception {
        String json = "";
        //执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
        mockMvc.perform(MockMvcRequestBuilders
                .post("/hello2")
                .content(json.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer ********-****-****-****-************")
        )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                System.err.println(result.getAsyncResult());
            }
        });
        ;
    }

    @Test
    public void get() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/XXX/get")
                .param("id", "**********")
                .header("Authorization", "Bearer ********-****-****-****-************")
        );
        resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                System.err.println(result.getAsyncResult());
            }
        });
    }
}
