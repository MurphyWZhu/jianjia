package com.baima.jianjia.controller;

import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserInfoController {
    @Autowired
    UserserviceImpl userService;

    @PostMapping(value = "/getuserinfo")
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
    @RequestMapping(value = "subuserinfo")
    public ModelAndView UserInfo(Model model){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        model.addAttribute("userinfo",userinfo);
        return new ModelAndView("subuserinfo");
    }
    @RequestMapping(value = "userselfspace")
    public ModelAndView UserSelfSpace(Model model){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        List<UserShow> userShows = userService.getSelfShows(user.username);
        model.addAttribute("userShows", userShows);
        model.addAttribute("userinfo",userinfo);
        return new ModelAndView("subuserselfspace");
    }
    @PostMapping(value = "userspace")
    public ModelAndView userSpace(String username,Model model){
        Userinfo userinfo = userService.getUserInfobyName(username);
        List<UserShow> userShows = userService.getUserShows(username);
        model.addAttribute("userShows", userShows);
        model.addAttribute("userinfo",userinfo);
        return new ModelAndView("subuserspace");
    }
}
