package org.jacob.spring.es2;

import javax.annotation.Resource;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ReverseNestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.jacob.spring.es2.bean.ShakespeareBean;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClass8 {

	private static final Logger log = LoggerFactory.getLogger(TestClass8.class);

	@Resource
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Resource
	private RestHighLevelClient highLevelClient;

	// 方法
	/**
	 * 聚合查询
	 * 
	 * 桶Buckets：类似于group by分组后的结果放入一个一个的桶中
	 * 
	 * 指标Metrics：类似于min，max、sum筛选计算。
	 */
	@Test
	public void query_matchQuery() {

		// 去重统计某个字段的数量（有少量误差）
		CardinalityAggregationBuilder cb = AggregationBuilders.cardinality("distinct_count_uid").field("uid");

		// 聚合过滤
		FilterAggregationBuilder fab = AggregationBuilders.filter("uid_filter",
				QueryBuilders.queryStringQuery("uid:001"));
		// 按某个字段分组
		TermsAggregationBuilder tb = AggregationBuilders.terms("group_name").field("speaker.keyword").size(200);

		// 求和
		SumAggregationBuilder sumBuilder = AggregationBuilders.sum("sum_price").field("price");

		// 求平均
		AvgAggregationBuilder ab = AggregationBuilders.avg("avg_price").field("price");

		// 求最大值
		MaxAggregationBuilder mb = AggregationBuilders.max("max_price").field("price");
		// 求最小值
		MinAggregationBuilder min = AggregationBuilders.min("min_price").field("price");

		// 按日期间隔分组
		DateHistogramAggregationBuilder dhb = AggregationBuilders.dateHistogram("dh").field("date");

		// 获取聚合里面的结果
		TopHitsAggregationBuilder thb = AggregationBuilders.topHits("top_result");

		// 嵌套的聚合
		NestedAggregationBuilder nb = AggregationBuilders.nested("name", "negsted_path");

		// 反转嵌套
		ReverseNestedAggregationBuilder re = AggregationBuilders.reverseNested("res_negsted").path("kps");

		// 统计某个字段的数量
		ValueCountAggregationBuilder vcb = AggregationBuilders.count("line_id_count").field("line_id");

		IndexCoordinates index = IndexCoordinates.of("shakespeare");

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().addAggregation(tb).build();
		SearchHits<ShakespeareBean> searchHits = elasticsearchRestTemplate.search(searchQuery, ShakespeareBean.class,
				index);
		searchHits.get().forEach(hit -> {
			//log.info("result==============={}", hit.getContent());
		});
		// 获取聚合结果
		if (searchHits.hasAggregations()) {
			ParsedStringTerms tr = searchHits.getAggregations().get("group_name");
			tr.getBuckets().forEach(bu->{
				log.info("============={}==========={}",bu.getKeyAsString(),bu.getDocCount());
			});
			ParsedValueCount parsedValueCount = searchHits.getAggregations().get("group_name");
			Assertions.assertNotNull(parsedValueCount, "无聚合结果");
			log.info("===================={}", parsedValueCount.getValue());
		}
	}

}
