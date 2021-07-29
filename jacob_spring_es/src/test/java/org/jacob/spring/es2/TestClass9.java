package org.jacob.spring.es2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass9 {

	private static final Logger log = LoggerFactory.getLogger(TestClass9.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Resource
	private RestHighLevelClient highLevelClient;

	// 方法
	// 精确匹配
	@Test
	public void query_matchQuery() {
		// Select from shakespeare where play_name = "5.5.79"

		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

		// 排序查询
		FieldSortBuilder sort = SortBuilders.fieldSort("line_id").order(SortOrder.DESC).unmappedType("string");

		Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSort(sort).build();

		IndexCoordinates index = IndexCoordinates.of("shakespeare");
		List<ShakespeareBean> result = new ArrayList<>();
		List<String> scrollids = new ArrayList<>();
		SearchScrollHits<ShakespeareBean> hits = elasticsearchRestTemplate.searchScrollStart(5000, searchQuery,
				ShakespeareBean.class, index);
		while (hits.hasSearchHits()) {
			result.addAll(hits.get().map(SearchHit::getContent).collect(Collectors.toList()));
			String scrollid = hits.getScrollId();
			scrollids.add(scrollid);
			hits = elasticsearchRestTemplate.searchScrollContinue(scrollid, 5000, ShakespeareBean.class, index);

		}
		elasticsearchRestTemplate.searchScrollClear(scrollids);
		result.forEach(hh -> {
			log.info("========={}", hh);
		});
		log.info("result ==============size========={}", result.size());

	}

}
