package com.baima.jianjia.controller;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class SearchUserListController {
    @Autowired
    UserserviceImpl userService;
    @PostMapping(value = "/tosearch")
    public ModelAndView searchUserList(String key,Model model){
        List<Userinfo> userinfoList = userService.searchKey(key);
        userinfoList.addAll(userService.searchNikeName(key));
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        String username;
        if (user == null) {
            username = null;
            
        }else{
            username = user.username;
        }
        List<String> likeUserNameList = userService.getLikeList(username);
        for (Userinfo userinfo : userinfoList) {
            userinfo.likeit = likeUserNameList.contains(userinfo.username);
        }
        System.out.println(key);
        model.addAttribute("userinfoList",userinfoList);
        return new ModelAndView("search");
    }
}
