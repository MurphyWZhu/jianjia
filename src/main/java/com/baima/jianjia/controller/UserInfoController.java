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
    public User getUserByBBS(){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user;
    }
    public class userShowAndInfo{
        public Userinfo userinfo;
        public UserShow userShow;
    }
    @Autowired
    UserserviceImpl userService;

    @RequestMapping(value = "userselfspace")
    public ModelAndView UserSelfSpace(Model model) {
        User user = this.getUserByBBS();
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        System.out.println(userinfo.profilepicture);
        List<UserShow> userShows = userService.getSelfShows(user.username);
        List<userShowAndInfo> usershowandinfos = new ArrayList<>();
        for (UserShow userShow : userShows) {
            userShowAndInfo usershowandinfo = new userShowAndInfo();
            usershowandinfo.userShow = userShow;
            usershowandinfo.userinfo = userinfo;
            usershowandinfos.add(usershowandinfo);
        }
        model.addAttribute("usershowandinfos", usershowandinfos);
        model.addAttribute("userinfoself", userinfo);
        return new ModelAndView("userselfspace");
    }

    @RequestMapping(value = "userspace")
    public ModelAndView userSpace(String username, Model model) {
        Userinfo userinfo = userService.getUserInfobyName(username);
        List<UserShow> userShows = userService.getUserShows(username);
        List<userShowAndInfo> usershowandinfos = new ArrayList<>();
        for (UserShow userShow : userShows) {
            userShowAndInfo usershowandinfo = new userShowAndInfo();
            usershowandinfo.userShow = userShow;
            usershowandinfo.userinfo = userinfo;
            usershowandinfos.add(usershowandinfo);
        }
        model.addAttribute("usershowandinfos", usershowandinfos);
        model.addAttribute("userinfoself", userinfo);
        return new ModelAndView("userspace");
    }
}
