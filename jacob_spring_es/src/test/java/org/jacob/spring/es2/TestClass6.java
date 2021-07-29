package org.jacob.spring.es2;

import javax.annotation.Resource;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
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
public class TestClass6 {

	private static final Logger log = LoggerFactory.getLogger(TestClass6.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Resource
	private RestHighLevelClient highLevelClient;

	// 方法
	// 多条件查询
	@Test
	public void query_matchQuery() {

		/*
		 * 组合查询BoolQueryBuilder
		 * 
		 * must(QueryBuilders) :AND
		 * 
		 * mustNot(QueryBuilders) :NOT
		 * 
		 * should: :OR
		 */

		// Select * from book where author = “年轻” or count =1

		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		builder.must(QueryBuilders.queryStringQuery("年轻").field("author"));
		builder.should(QueryBuilders.matchQuery("count", 1));
		builder.mustNot(QueryBuilders.matchQuery("count", 2));
		// 一个字段匹配多个值
		builder.must(QueryBuilders.termsQuery("desc", "年", "书"));
		//一个值匹配多个字段
		builder.must(QueryBuilders.multiMatchQuery("小", "author", "desc"));
		
		// 排序查询
		FieldSortBuilder sort = SortBuilders.fieldSort("count").order(SortOrder.ASC);
		Query searchQuery = new NativeSearchQueryBuilder().withQuery(builder).withSort(sort).build();
		IndexCoordinates index = IndexCoordinates.of("shakespeare");
		SearchHits<ShakespeareBean> hits = elasticsearchRestTemplate.search(searchQuery, ShakespeareBean.class, index);

		for (SearchHit<ShakespeareBean> hitsHit : hits.getSearchHits()) {
			log.info("查询出來的結果============={}", hitsHit.getContent());
		}
		log.info("list==============size==========={}", hits.getSearchHits().size());

	}

}
