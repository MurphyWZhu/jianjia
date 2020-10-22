package com.baima.jianjia.controller;

import java.util.ArrayList;
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
    public class userShowandInfo {
        public String username;
        public String nikename;
        public String profilepicture;
        public String sex;
        public String department;
        public String showdata;
        public String timedate;
        public int showid;
    }

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
    public ModelAndView UserInfo(Model model) {
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        model.addAttribute("userinfo", userinfo);
        return new ModelAndView("subuserinfo");
    }

    @RequestMapping(value = "userselfspace")
    public ModelAndView UserSelfSpace(Model model) {
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        System.out.println(userinfo.profilepicture);
        List<UserShow> userShows = userService.getSelfShows(user.username);
        List<userShowandInfo> uShowandInfos = new ArrayList<>();
        for (UserShow userShow : userShows) {
            userShowandInfo uShowandInfo = new userShowandInfo();
            uShowandInfo.username = userShow.username;
            uShowandInfo.showdata = userShow.showdata;
            uShowandInfo.timedate = userShow.timedate;
            uShowandInfo.showid = userShow.id;
            uShowandInfo.nikename = userinfo.nikename;
            uShowandInfo.profilepicture = userinfo.profilepicture;
            uShowandInfo.department = userinfo.department;
            uShowandInfo.sex = userinfo.sex;
            uShowandInfos.add(uShowandInfo);
        }
        System.out.println(uShowandInfos.get(0).showdata);
        model.addAttribute("userinfoself", userinfo);
        model.addAttribute("uShowandInfos", uShowandInfos);
        return new ModelAndView("userselfspace");
    }

    @PostMapping(value = "userspace")
    public ModelAndView userSpace(String username, Model model) {
        Userinfo userinfo = userService.getUserInfobyName(username);
        List<UserShow> userShows = userService.getUserShows(username);
        model.addAttribute("userShows", userShows);
        model.addAttribute("userinfo", userinfo);
        return new ModelAndView("subuserspace");
    }
}
