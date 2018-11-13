package com.donwae.market.melon.controller;

import com.donwae.market.entity.User;
import com.donwae.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆 Api
 * @auther Jeremy
 * 2018/11/12 下午4:38
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 展示用户
     * 2018/11/12 16:42:42
     * @author Jeremy Zhang
     */
    @GetMapping("/show")
    public User importExcel(String name) {
        name = "zhangsan";
        return userService.findByName(name);
    }

}
