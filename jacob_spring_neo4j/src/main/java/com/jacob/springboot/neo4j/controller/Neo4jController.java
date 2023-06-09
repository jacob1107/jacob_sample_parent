package com.jacob.springboot.neo4j.controller;

import com.jacob.springboot.neo4j.service.UserService;
import com.jacob.springboot.neo4j.entity.UserNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Neo4jController {

    @Autowired
    private UserService userService;

    @RequestMapping("/saveUser")
    public String saveUserNode() {

        UserNode node = new UserNode();
        node.setNodeId(1l);
        node.setUserId("2");
        node.setName("赵六");
        node.setAge(26);

        userService.addUserNode(node);
        return "success";       
    }


    @RequestMapping("/addship")
    public String saveShip(){
        userService.addRelationship("1","2");
        return "success";
    }

    @RequestMapping("/findShip")
    public String findShip(){
        userService.findUserRelationByEachId("1","2");
        return "success";
    }

}
