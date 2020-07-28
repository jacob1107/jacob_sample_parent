package org.jacob.spring.es2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.jacob.spring.es2.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass {

	private static final Logger log = LoggerFactory.getLogger(TestClass.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

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
		book.setName("java从入门到入土");
		book.setAuthor("詹姆斯res高级大神");
		Book book1 = new Book();
		book1.setId("102");
		book1.setName("java从入门到入土");
		book1.setAuthor("詹姆斯高斯林");
		Book book2 = new Book();
		book2.setId("103");
		book2.setName("java从入门到入土");
		book2.setAuthor("詹姆斯高斯林");
		Book book3 = new Book();
		book3.setId("104");
		book3.setName("java从入门到入土");
		book3.setAuthor("詹姆斯高斯林");
		books.add(book);
		books.add(book1);
		books.add(book2);
		books.add(book3);
		for (Book book4 : books) {
			IndexQuery indexQuery = new IndexQuery();
			indexQuery.setIndexName("myindex");
			indexQuery.setObject(book4);
			String index = elasticsearchRestTemplate.index(indexQuery);
			System.out.println(index);
		}

	}

	@Test
	public void queryByHighLight() {

//        ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate();
		String message = "詹姆斯";
		String field = "author";
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.termQuery(field, message))
				.withHighlightFields(new HighlightBuilder.Field(field)).build();
		AggregatedPage<Book> books = elasticsearchRestTemplate.queryForPage(searchQuery, Book.class,
				new SearchResultMapper() {
					@SuppressWarnings("unchecked")
					@Override
					public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass,
							Pageable pageable) {
						ArrayList<Book> books = new ArrayList<>();
						SearchHits hits = response.getHits();
						for (SearchHit searchHit : hits) {
							if (hits.getHits().length <= 0) {
								return null;
							}
							Book book = new Book();
							String highLightMessage = searchHit.getHighlightFields().get(field).fragments()[0]
									.toString();
							book.setId(searchHit.getId());
							book.setName(String.valueOf(searchHit.getSourceAsMap().get("name")));
							// 反射调用set方法将高亮内容设置进去
							try {
								String setMethodName = parSetName(field);
								Class<? extends Book> bookClazz = book.getClass();
								java.lang.reflect.Method setMethod = bookClazz.getMethod(setMethodName, String.class);
								setMethod.invoke(book, highLightMessage);
							} catch (Exception e) {
								e.printStackTrace();
							}
							books.add(book);
						}
						if (books.size() > 0) {
							return new AggregatedPageImpl<T>((List<T>) books);
						}
						return null;
					}

					@Override
					public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
						return null;
					}
				});
		log.info("========queryByHighLight==========={}", books.getContent());
	}

	private static String parSetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		int startIndex = 0;
		if (fieldName.charAt(0) == '_')
			startIndex = 1;
		return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
				+ fieldName.substring(startIndex + 1);
	}

}
