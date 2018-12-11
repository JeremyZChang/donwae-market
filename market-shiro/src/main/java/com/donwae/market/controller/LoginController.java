package com.donwae.market.controller;

import com.donwae.market.entity.User;
import com.donwae.market.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * TODO Login&Security Control
 * @auther Jeremy
 * 2018/11/13 上午10:42
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getName(), user.getPassword());
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);

        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/logout";
    }

    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping("/adminCreate")
    @ResponseBody
    public String adminCreate() {
        return "只有[admin]身份且具有[create]权限才能看见这句话";
    }

    //错误页面展示
    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error ok!";
    }
}
