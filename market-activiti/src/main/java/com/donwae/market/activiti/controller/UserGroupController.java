package com.donwae.market.activiti.controller;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author Jeremy Zhang
 * 2020/5/12 下午11:47
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserGroupController {

    @Autowired
    private IdentityService identityService;

    //2、创建Activiti用户
    @GetMapping("/addUser")
    public void addUser( ){
        //项目中每创建一个新用户，对应的要创建一个Activiti用户,两者的userId和userName一致

        //添加用户
        User user1 = identityService.newUser("user1");
        user1.setFirstName("张三");
        user1.setLastName("张");
        user1.setPassword("123456");
        user1.setEmail("zhangsan@qq.com");
        identityService.saveUser(user1);

        User user2 = identityService.newUser("user2");
        user2.setFirstName("李四");
        user2.setLastName("李");
        user2.setPassword("123456");
        user2.setEmail("lisi@qq.com");
        identityService.saveUser(user2);

        User user3 = identityService.newUser("user3");
        user3.setFirstName("王五");
        user3.setLastName("王");
        user3.setPassword("123456");
        user3.setEmail("wangwu@qq.com");
        identityService.saveUser(user3);

        User user4 = identityService.newUser("user4");
        user4.setFirstName("吴六");
        user4.setLastName("吴");
        user4.setPassword("123456");
        user4.setEmail("wuliu@qq.com");
        identityService.saveUser(user4);

    }

    //3、根据id查询Activiti用户
    @GetMapping("/queryUser")
    public void queryUser( ){

        User user = identityService.createUserQuery().userId("user1").singleResult();
        System.out.println(user.getId());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());

    }

    //4、创建Activiti用户组
    @GetMapping("/addGroup")
    public void addGroup( ){

        Group group1 = identityService.newGroup("group1");
        group1.setName("员工组");
        group1.setType("员工组");
        identityService.saveGroup(group1);

        Group group2 = identityService.newGroup("group2");
        group2.setName("总监组");
        group2.setType("总监阻");
        identityService.saveGroup(group2);

        Group group3 = identityService.newGroup("group3");
        group3.setName("经理组");
        group3.setType("经理组");
        identityService.saveGroup(group3);

        Group group4 = identityService.newGroup("group4");
        group4.setName("人力资源组");
        group4.setType("人力资源组");
        identityService.saveGroup(group4);

    }

    //5、通过用户组id查询Activiti用户组
    @GetMapping("/queryGroup")
    public void queryGroup( ){

        Group group = identityService.createGroupQuery().groupId("group1").singleResult();
        System.out.println(group.getId());
        System.out.println(group.getName());
        System.out.println(group.getType());

    }

    //6、创建Activiti（用户-用户组）关系
    @GetMapping("/addMembership")
    public void addMembership( ){

        identityService.createMembership("user1", "group1");//user1 在员工阻
        identityService.createMembership("user2", "group2");//user2在总监组
        identityService.createMembership("user3", "group3");//user3在经理组
        identityService.createMembership("user4", "group4");//user4在人力资源组

    }

    //7、查询属于组group1的用户
    @GetMapping("/queryUserListByGroup")
    public void queryUserListByGroup( ){

        //查询属于组group1的用户
        List<User> usersInGroup = identityService.createUserQuery().memberOfGroup("group1").list();
        for (User user : usersInGroup) {
            System.out.println(user.getFirstName());
        }

    }

    //8、查询user1所属于的组
    @GetMapping("/queryGroupListByUser")
    public void queryGroupListByUser( ){

        //查询user1所属于的组
        List<Group> groupsForUser = identityService.createGroupQuery().groupMember("user1").list();
        for (Group group : groupsForUser) {
            System.out.println(group.getName());
        }

    }

}
