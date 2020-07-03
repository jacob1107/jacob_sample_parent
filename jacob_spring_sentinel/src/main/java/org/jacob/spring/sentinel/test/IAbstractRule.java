package org.jacob.spring.sentinel.test;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.fastjson.JSON;

public class IAbstractRule {

	public static void main(String[] args) {

		// authorityRule();

		 degradeRule();

		//flowRule();

		// paramFlowRule();
		// systemRule();

	}

	protected static void systemRule() {
		SystemRule systemRule = new SystemRule();
		systemRule.setAvgRt(1l);
		systemRule.setHighestCpuUsage(1.0);
		systemRule.setHighestSystemLoad(1.0);
		systemRule.setMaxThread(1l);
		systemRule.setQps(1.0);
		systemRule.setLimitApp("limitApp");
		systemRule.setResource("resource");
		System.err.println(systemRule.toString());
		System.err.println(JSON.toJSONString(systemRule));
	}

	protected static void paramFlowRule() {
		ParamFlowRule paramFlowRule = new ParamFlowRule();

		paramFlowRule.setBurstCount(19);
		paramFlowRule.setClusterMode(false);
		paramFlowRule.setControlBehavior(12);
		paramFlowRule.setCount(4);
		paramFlowRule.setDurationInSec(5l);
		paramFlowRule.setGrade(1);
		paramFlowRule.setMaxQueueingTimeMs(23);
		paramFlowRule.setParamIdx(1);
		paramFlowRule.setLimitApp("default");
		paramFlowRule.setResource("say");
		ParamFlowItem item = new ParamFlowItem();
		item.setClassType(String.class.getTypeName());
		item.setObject("sex");
		item.setCount(7);
		ParamFlowItem item1 = new ParamFlowItem();
		item1.setClassType(int.class.getTypeName());
		item1.setCount(6);
		item1.setObject("age");
		List<ParamFlowItem> paramFlowItemList = new LinkedList<>();
		paramFlowItemList.add(item);
		paramFlowItemList.add(item1);
		paramFlowRule.setParamFlowItemList(paramFlowItemList);
		System.err.println(paramFlowRule.toString());
		System.err.println(JSON.toJSONString(paramFlowRule));

	}

	protected static void flowRule() {
		FlowRule flowRule = new FlowRule();

		flowRule.setLimitApp("default");
		flowRule.setResource("say");
		flowRule.setCount(1.0);

		flowRule.setGrade(1);
		flowRule.setStrategy(1);
		flowRule.setControlBehavior(1);
		flowRule.setClusterMode(false);
		flowRule.setMaxQueueingTimeMs(1);
		flowRule.setWarmUpPeriodSec(1);

		System.err.println(flowRule.toString());
		System.err.println(JSON.toJSONString(flowRule));
	}

	protected static void degradeRule() {
		DegradeRule degradeRule = new DegradeRule();
		degradeRule.setCount(1.0);
		degradeRule.setGrade(1);
		degradeRule.setLimitApp("limitApp");
		degradeRule.setResource("resource");
		degradeRule.setTimeWindow(1);
		System.err.println(degradeRule.toString());
		System.err.println(JSON.toJSONString(degradeRule));
	}

	protected static void authorityRule() {
		AuthorityRule authorityRule = new AuthorityRule();
		authorityRule.setLimitApp("default");
		authorityRule.setResource("say");
		// Mode: 0 for whitelist; 1 for blacklist
		authorityRule.setStrategy(1);

		System.err.println(authorityRule.toString());
		System.err.println(JSON.toJSONString(authorityRule));
	}

}
