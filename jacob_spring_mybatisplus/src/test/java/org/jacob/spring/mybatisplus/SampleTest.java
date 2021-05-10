package org.jacob.spring.mybatisplus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jacob.spring.mybatisplus.data.User;
import org.jacob.spring.mybatisplus.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));
		Map<String, Object> columnMap= new HashMap<>();
		columnMap.put("name", "Tom");
		userMapper.selectByMap(columnMap);
		QueryWrapper<User> wrapper =new QueryWrapper<User>() ;
		
		wrapper.eq("name", "Tom").select("name", "age");
		List<User> userList = userMapper.selectList(wrapper);
		Assert.assertEquals(1, userList.size());
		userList.forEach(System.out::println);
	}

}