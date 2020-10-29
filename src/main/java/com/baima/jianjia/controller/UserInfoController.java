package com.baima.jianjia.controller;

import java.util.ArrayList;
import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.service.UserInfoServiceImpl;
import com.baima.jianjia.service.UserShowServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserInfoController {
    public class userShowAndInfo{
        public UserInfo userinfo;
        public UserShow userShow;
    }
    @Autowired
    UserInfoServiceImpl userInfoService;

    @Autowired
    UserShowServiceImpl userShowService;
    @RequestMapping(value = "userselfspace")
    public ModelAndView UserSelfSpace(Model model) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        UserInfo userinfo = userInfoService.getUserInfo(user);
        System.out.println(userinfo.profilepicture);
        List<UserShow> userShows = userShowService.getSelfShows(user.username);
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
        UserInfo userinfo = userInfoService.getUserInfobyName(username);
        List<UserShow> userShows = userShowService.getUserShows(username);
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
