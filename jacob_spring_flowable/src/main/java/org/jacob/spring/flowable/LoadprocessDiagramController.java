package org.jacob.spring.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 加载流程图
 *
 * @author jacob
 */
@RestController
@RequestMapping("/loadprocessDiagram")
public class LoadprocessDiagramController {
    private static final Logger logger = LoggerFactory.getLogger(LoadprocessDiagramController.class);
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
     * 加载流程图
     *
     * @param resourceName
     * @param text
     * @throws Exception
     */
    @RequestMapping("/loadprocessDiagramresourceName")
    public void loadprocessDiagramresourceName(String resourceName, String text) throws Exception {
        Deployment deployment = repositoryService.createDeployment()
                //.addClasspathResource("processes/ExpenseProcess.bpmn20.xml")
                .addString(resourceName, text)
                .deploy();
    }

    /**
     * 加载流程图
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadprocessDiagramresourceNamePath")
    public Deployment loadprocessDiagramresourceNamePath(@RequestParam("file") MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        String resourceName = file.getOriginalFilename();
        Deployment deployment = repositoryService.createDeployment()
                //.addClasspathResource("processes/ExpenseProcess.bpmn20.xml")
                .addInputStream(resourceName, inputStream)
                .name(resourceName)
                .category(resourceName)
                .key(resourceName)
                .deploy();
        logger.info(deployment.getId());
        return deployment;
    }

    @RequestMapping("/loadprocessDiagramresourceNamePath2")
    public Deployment loadprocessDiagramresourceNamePath2(@RequestParam("file") String file, @RequestParam("resourceName") String resourceName) throws Exception {
        InputStream inputStream = new FileInputStream(new File(file));
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream(resourceName, inputStream)
                .name(resourceName)
                .category(resourceName)
                .key(resourceName)
                .deploy();
        logger.info(deployment.getId());
        return deployment;
    }


}