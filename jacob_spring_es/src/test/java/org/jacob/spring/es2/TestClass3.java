package org.jacob.spring.es2;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.jacob.spring.es2.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass3 {

	private static final Logger log = LoggerFactory.getLogger(TestClass3.class);

	@Resource
	private ElasticsearchTemplate elasticsearchRestTemplate;

	// 方法
	// 删除index
	@Test
	public void deleteIndex() {
		boolean b = elasticsearchRestTemplate.deleteIndex("myindex");
		log.info("========deleteIndex==========={}", b);
	}

	// 创建index
	@Test
	public void createIndex() {
		boolean b = elasticsearchRestTemplate.createIndex("myindex");
		log.info("========createIndex==========={}", b);
	}

	// 创建mapping
	@Test
	public void createMapping() {
		boolean b = elasticsearchRestTemplate.putMapping(Book.class);
		log.info("========createMapping==========={}", b);
	}

	// 查看表字段
	@Test
	public void getMapping() {
		Map<String, Object> mapping = elasticsearchRestTemplate.getMapping(Book.class);
		log.info("========getMapping==========={}", mapping);
	}

	@Test
	public void saveListToES() {
		ArrayList<Book> books = new ArrayList<>();
		Book book = new Book();
		book.setId("101");
		book.setName("java从入门到入土333333");
		book.setAuthor("詹姆斯res高级大神");
		Book book1 = new Book();
		book1.setId("102");
		book1.setName("java从入门到入土5");
		book1.setAuthor("詹姆斯高斯林");
		Book book2 = new Book();
		book2.setId("103");
		book2.setName("java从入门到入土6");
		book2.setAuthor("詹姆斯高斯林");
		Book book3 = new Book();
		book3.setId("104");
		book3.setName("java从入门到入土7");
		book3.setAuthor("詹姆斯高斯林");
		books.add(book);
		books.add(book1);
		books.add(book2);
		books.add(book3);
		for (Book book4 : books) {
			elasticsearchRestTemplate.save(book4, IndexCoordinates.of("jacob-5603"));
			/*
			 * IndexQuery indexQuery = new IndexQuery(); indexQuery.setIndexName("myindex");
			 * indexQuery.setObject(book4); indexQuery.setId(UUID); String index =
			 * elasticsearchRestTemplate.index(indexQuery); System.out.println(index);
			 */

		}
		Book book22 = elasticsearchRestTemplate.get("101", Book.class, IndexCoordinates.of("jacob-5603"));
		book22.setAuthor("rrrrrrrrrrrrrrrrrrrrrr");
		elasticsearchRestTemplate.save(book22, IndexCoordinates.of("jacob-5603"));

	}

}
