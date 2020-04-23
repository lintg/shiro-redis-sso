package com.shiroredis.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiroredis.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public User login(@RequestParam(value = "username") String username,
                      @RequestParam(value = "password") String password){
        Subject subject = SecurityUtils.getSubject();
        
        subject.login(new UsernamePasswordToken(username, password));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SecurityUtils.getSubject().getSession().setAttribute("test", "test123");
        return user;
    }

    @RequestMapping("/logout")
    public Boolean logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return true;
    }

    //开启Shiro的注解
    @RequiresPermissions(value="/user/getUser")
    @RequestMapping("/getUser")
    public User getUser(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String str = (String)SecurityUtils.getSubject().getSession().getAttribute("test");
        System.out.println("------getUser:"+str);
        return user;
    }
    
    @RequestMapping("/get")
    public User get(){
    	User user = (User) SecurityUtils.getSubject().getPrincipal();
    	String str = (String)SecurityUtils.getSubject().getSession().getAttribute("test");
    	System.out.println(str);
    	return user;
    }

}
