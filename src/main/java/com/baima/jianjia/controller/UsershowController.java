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
public class UsershowController {
    public class userShowandInfo{
        public String username;
        public String nikename;
        public String profilepicture;
        public String sex;
        public String department;
        public String showdata;
        public String timedate;
    }
    @Autowired
    UserserviceImpl userService;
    @PostMapping(value = "/postshow")
    public String postShow(String showdata,Boolean ispublic){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        String username;
        if (user == null) {
            username = null;
            
        }else{
            username = user.username;
        }
        userService.postShow(username, showdata, ispublic);
        return "hello";
    }
    @RequestMapping("/postshowbox")
    public ModelAndView postShowBox(){
        return new ModelAndView("postshowbox");
    }
    @PostMapping(value = "/getallusershows")
    public ModelAndView getAllUserShows(Model model){
        List<UserShow> allUserShows = userService.getAllUserShows();
        List<userShowandInfo> uShowandInfos = new ArrayList<>();
        for (UserShow userShow : allUserShows) {
            userShowandInfo uShowandInfo = new userShowandInfo();
            uShowandInfo.username=userShow.username;
            uShowandInfo.showdata=userShow.showdata;
            uShowandInfo.timedate=userShow.timedate;
            Userinfo userinfo = userService.getUserInfobyName(userShow.username);
            uShowandInfo.nikename=userinfo.nikename;
            uShowandInfo.profilepicture=userinfo.profilepicture;
            uShowandInfo.department=userinfo.department;
            uShowandInfo.sex=userinfo.sex;
            uShowandInfos.add(uShowandInfo);
        }
        model.addAttribute("uShowandInfos",uShowandInfos);
        return new ModelAndView("suballusershows");
    }
    @PostMapping(value = "/getselfshows")
    public ModelAndView getSelfShows(Model model){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        String username;
        if (user == null) {
            username = null;
            
        }else{
            username = user.username;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        List<UserShow> userShows = userService.getSelfShows(username);
        model.addAttribute("userShows", userShows);
        model.addAttribute("userinfo", userinfo);
        return new ModelAndView("subselfshow");
    }
    @PostMapping(value = "/getusershows")
    public ModelAndView getUserShows(String username,Model model){
        return new ModelAndView("subusershow");
    }
}
