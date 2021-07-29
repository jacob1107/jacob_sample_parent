package org.jacob.spring.es2;

import javax.annotation.Resource;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.jacob.spring.es2.bean.ShakespeareBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass7 {

	private static final Logger log = LoggerFactory.getLogger(TestClass7.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Resource
	private RestHighLevelClient highLevelClient;

	// 方法
	// 范围查询
	@Test
	public void query_matchQuery() {

		// Select * from book where author = “年轻” or count =1

		// 闭区间查询
		QueryBuilder builder = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2");

		// 开区间查询
		QueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("fieldName").from("fieldValue1").to("fieldValue2")
				.includeUpper(false).includeLower(false);// 默认是true，也就是包含
		// 大于
		QueryBuilder queryBuilder2 = QueryBuilders.rangeQuery("fieldName").gt("fieldValue");

		// 大于等于
		QueryBuilder queryBuilder3 = QueryBuilders.rangeQuery("fieldName").gte("fieldValue");

		// 小于
		QueryBuilder queryBuilder4 = QueryBuilders.rangeQuery("fieldName").lt("fieldValue");

		// 小于等于
		QueryBuilder queryBuilder5 = QueryBuilders.rangeQuery("fieldName").lte("fieldValue");

		// 排序查询
		FieldSortBuilder sort = SortBuilders.fieldSort("count").order(SortOrder.ASC).unmappedType("string");
		Query searchQuery = new NativeSearchQueryBuilder().withQuery(builder).withSort(sort).build();
		IndexCoordinates index = IndexCoordinates.of("shakespeare");
		SearchHits<ShakespeareBean> hits = elasticsearchRestTemplate.search(searchQuery, ShakespeareBean.class, index);

		for (SearchHit<ShakespeareBean> hitsHit : hits.getSearchHits()) {
			log.info("查询出來的結果============={}", hitsHit.getContent());
		}
		log.info("list==============size==========={}", hits.getSearchHits().size());

	}

}
