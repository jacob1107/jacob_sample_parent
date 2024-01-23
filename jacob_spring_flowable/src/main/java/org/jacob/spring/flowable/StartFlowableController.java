package org.jacob.spring.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author jacob
 */
@RestController
@RequestMapping("/expense")
public class StartFlowableController {
    private static final Logger logger = LoggerFactory.getLogger(StartFlowableController.class);
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;

    @Qualifier("processEngine")
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 通过接收用户的一个请求传入用户的ID和金额以及描述信息来开启一个报销流程，并返回给用户这个流程的Id
     * 添加报销
     *
     * @param userId    用户Id
     * @param money     销金额
     * @param descption 描述
     */
    @RequestMapping("/add")
    public String addExpense(String userId, Integer money, String descption) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }

    @RequestMapping("/addByKey")
    public String addExpenseByKey(String userId, Integer money, String descption, String processInstanceByKey) {
        //启动流程
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey, map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }


}