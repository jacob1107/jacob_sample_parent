package org.jacob.spring.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jacob
 */
@RestController
@RequestMapping("/expense")
public class FlowableController {
    private static final Logger logger = LoggerFactory.getLogger(FlowableController.class);
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
     * 查询流程列表，待办列表，通过代码获取出用户需要处理的流程
     * 获取审批管理列表
     */
    @RequestMapping("/list")
    public Object list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        return "task size: " + tasks.size() + " , 第一个：" + tasks.get(0).toString();
    }

    /**
     * 批准，通过前端传入的任务ID来对此流程进行同意处理
     *
     * @param taskId 任务ID
     */
    @RequestMapping("/apply")
    public String apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return "processed ok!";
    }

    /**
     * 拒绝
     */
    @RequestMapping("/reject")
    public String reject(String taskId) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return "reject";
    }


}