package org.jacob.spring.es2;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.jacob.spring.es2.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass10 {

	private static final Logger log = LoggerFactory.getLogger(TestClass10.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Resource
	private RestHighLevelClient highLevelClient;

	// 方法
	// 删除index
	@Test
	public void deleteIndex() {
		boolean b = elasticsearchRestTemplate.deleteIndex("myindex");
		log.info("========deleteIndex==========={}", b);
	}

	// 方法
	// 删除index
	@Test
	public void deleteIndex1() throws Exception {
		GetAliasesRequest request = new GetAliasesRequest();
		GetAliasesResponse getAliasesResponse = highLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
		Map<String, Set<AliasMetaData>> map = getAliasesResponse.getAliases();
		Set<String> indices = map.keySet();
		for (String key : indices) {
			System.out.println(key);
		}
	}

	// 创建index
	@Test
	public void createIndex() {
		IndexOperations tt = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("j9"));
		Document settings = Document.create();
		settings.put("index.number_of_shards", 1);
		settings.put("index.number_of_replicas", 1);
		tt.create(settings);
	  
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
			elasticsearchRestTemplate.save(book4, IndexCoordinates.of("jacob-5602"));
			/*
			 * IndexQuery indexQuery = new IndexQuery(); indexQuery.setIndexName("myindex");
			 * indexQuery.setObject(book4); indexQuery.setId(UUID); String index =
			 * elasticsearchRestTemplate.index(indexQuery); System.out.println(index);
			 */

		}
		Book book22 = elasticsearchRestTemplate.get("101", Book.class, IndexCoordinates.of("jacob-5602"));
		book22.setAuthor("rrrrrrrrrrrrrrrrrrrrrr");
		elasticsearchRestTemplate.save(book22, IndexCoordinates.of("jacob-5602"));

	}

}
