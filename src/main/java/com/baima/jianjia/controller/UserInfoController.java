package com.baima.jianjia.controller;


import com.baima.jianjia.pojo.User;

import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserInfoController {
    @Autowired
    UserserviceImpl userService;

    @RequestMapping(value = "/userinfo")
    public Userinfo getInteger() {
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        System.out.println(user.username);
        return userService.getUserInfo(user);
    }
}
