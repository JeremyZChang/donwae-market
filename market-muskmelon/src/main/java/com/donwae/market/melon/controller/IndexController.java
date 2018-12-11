package com.donwae.market.melon.controller;

import com.donwae.market.entity.User;
import com.donwae.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆 Api
 * @auther Jeremy
 * 2018/11/12 下午4:38
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private UserService userService;

    /**
     * 展示用户
     * 2018/11/12 16:42:42
     * @author Jeremy Zhang
     */
    @RequestMapping("/in")
    public String importExcel() {
        System.out.println("index");
        return "/index.html";
    }

}
