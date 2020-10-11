package com.baima.jianjia.controller;

import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
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
        model.addAttribute("allUserShows",allUserShows);
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
        return new ModelAndView("subselfshow");
    }
    @PostMapping(value = "/getusershows")
    public ModelAndView getUserShows(String username,Model model){
        return new ModelAndView("subusershow");
    }
}
