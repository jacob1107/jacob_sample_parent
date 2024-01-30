package com.jacob.flowable;

import com.google.gson.Gson;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.List;

//SpringBoot1.4版本之前用的是SpringJUnit4ClassRunner.class
@RunWith(SpringRunner.class)
//SpringBoot1.4版本之前用的是@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
//测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
@WebAppConfiguration
public class FlowableTest {
    @Qualifier("processEngine")
    @Autowired
    private ProcessEngine processEngine;
    
    @Test
    public void processEngine() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().orderByProcessDefinitionId().asc().list();
        for (ProcessDefinition processDefinition : list) {
            Gson gson = new Gson();
            System.err.println(gson.toJson(processDefinition));
        }
    }


    @Test
    public void deployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Deployment> list = repositoryService.createDeploymentQuery().orderByDeploymentId().asc().list();
        for (Deployment deployment : list) {
            Gson gson = new Gson();
            System.err.println(gson.toJson(deployment));
        }
    }

    @Test
    public void model() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Model> list = repositoryService.createModelQuery().orderByCreateTime().asc().list();
        for (Model model : list) {
            Gson gson = new Gson();
            System.err.println(gson.toJson(model));
        }
    }

    @Test
    public void startProcessInstanceById() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("taskUser", "userId");
        map.put("money", "500");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("Expense24:1:2aeb7c4f-be79-11ee-9411-76d83e96066d", map);
        Gson gson = new Gson();
        System.err.println(gson.toJson(processInstance.getDeploymentId()));
    }

    @Test
    public void tasksListManagerTask() {
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskAssignee("经理").orderByTaskCreateTime().desc().list();
        System.out.println(("task size: " + tasks.size() + " , 第一个：" + tasks.get(0).toString()));
        for (Task task : tasks) {
            System.err.println(new Gson().toJson(task));
        }
    }

    @Test
    public void rejectManagerTask() {
        String id = "a20e9925-be79-11ee-8813-76d83e96066d";
        Task task = processEngine.getTaskService().createTaskQuery().taskId(id).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("outcome", "驳回");
        processEngine.getTaskService().complete(id, map);
    }


    @Test
    public void tasksList() {
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskAssignee("userId").orderByTaskCreateTime().desc().list();
        System.out.println(("task size: " + tasks.size() + " , 第一个：" + tasks.get(0).toString()));
        for (Task task : tasks) {
            System.err.println(new Gson().toJson(task));
        }
    }

    @Test
    public void complete() {
        String id = "61cf9c18-be79-11ee-943c-76d83e96066d";
        Task task = processEngine.getTaskService().createTaskQuery().taskId(id).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("outcome", "通过");
        processEngine.getTaskService().complete(id, map);
    }
}