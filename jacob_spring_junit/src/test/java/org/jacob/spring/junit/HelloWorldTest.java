package org.jacob.spring.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//SpringBoot1.4版本之前用的是SpringJUnit4ClassRunner.class
@RunWith(SpringRunner.class)
//SpringBoot1.4版本之前用的是@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
//测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
@WebAppConfiguration
public class HelloWorldTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		// 实例化方式一
		//mockMvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
		// 实例化方式二
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testHello() throws Exception {

		/*
		 * 1、mockMvc.perform执行一个请求。
		 * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
		 * 3、ResultActions.param添加请求传值
		 * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
		 * 5、ResultActions.andExpect添加执行完成后的断言。
		 * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
		 * 比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
		 * 7、ResultActions.andReturn表示执行完成后返回相应的结果。
		 */
		mockMvc.perform(
				MockMvcRequestBuilders.get("/hello")
				// 设置返回值类型为utf-8，否则默认为ISO-8859-1
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.param("name", "Tom")
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Hello Tom!"))
				.andDo(MockMvcResultHandlers.print())
				//.andReturn()
						;
	}

}