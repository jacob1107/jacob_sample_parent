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
public class TestClass5 {

	private static final Logger log = LoggerFactory.getLogger(TestClass5.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Resource
	private RestHighLevelClient highLevelClient;

	// 方法
	// 模糊查询
	@Test
	public void query_StringQuery() {
		// Select from shakespeare where play_name like “%VI%”

		QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("VI").field("play_name");// 左右模糊
		Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		// 前缀查询 如果字段没分词，就匹配整个字段前缀
		QueryBuilders.prefixQuery("fieldName", "fieldValue");
		// 多字段模糊查询
		String[] fieldName = new String[2];
		fieldName[0] = "明";
		fieldName[1] = "年";
		QueryBuilders.moreLikeThisQuery(fieldName);

		// 通配符查询，支持* 任意字符串；？任意一个字符
		QueryBuilders.wildcardQuery("fieldName", "ctr*");// 前面是fieldname，后面是带匹配字符的字符串
		QueryBuilders.wildcardQuery("fieldName", "c?r?");
		
		

		IndexCoordinates index = IndexCoordinates.of("shakespeare");
		SearchHits<ShakespeareBean> hits = elasticsearchRestTemplate.search(searchQuery, ShakespeareBean.class, index);

		for (SearchHit<ShakespeareBean> hitsHit : hits.getSearchHits()) {
			log.info("查询出來的結果============={}", hitsHit.getContent());
		}
		log.info("list==============size==========={}", hits.getSearchHits().size());

	}

}
