package org.jacob.spring.es2;

import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
public class TestClass4 {

	private static final Logger log = LoggerFactory.getLogger(TestClass4.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Resource
	private RestHighLevelClient highLevelClient;

	// 方法
	// 精确匹配
	@Test
	public void query_matchQuery() {
		// Select from shakespeare where play_name = "5.5.79"

		QueryBuilder queryBuilder = QueryBuilders.matchQuery("play_name", "VI");
		
		
		
		
		Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

		
		IndexCoordinates index = IndexCoordinates.of("shakespeare");
		SearchHits<ShakespeareBean> hits = elasticsearchRestTemplate.search(searchQuery, ShakespeareBean.class, index);

		for (SearchHit<ShakespeareBean> hitsHit : hits.getSearchHits()) {
			log.info("查询出來的結果============={}", hitsHit.getContent());
		}
		log.info("list==============size==========={}", hits.getSearchHits().size());

		List<ShakespeareBean> list = elasticsearchRestTemplate.queryForList(searchQuery, ShakespeareBean.class, index);
		for (ShakespeareBean shakespeareBean : list) {
			log.info("查询出來的結果============={}", shakespeareBean);
		}
		log.info("list==============size==========={}", list.size());

	}

}
