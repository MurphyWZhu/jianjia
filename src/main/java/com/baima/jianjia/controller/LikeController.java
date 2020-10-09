package com.baima.jianjia.controller;

import java.util.ArrayList;
import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class LikeController {
    @Autowired
    UserserviceImpl userServer;
    @RequestMapping(value="/like", method=RequestMethod.GET)
    public ModelAndView getLikeList(Model model) {
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        List<String> likeUserNameList = userServer.getLikeList(user.username);
        List<Userinfo> likeUserInfoList = new ArrayList<Userinfo>();
        for (String likeUserName : likeUserNameList) {
            likeUserInfoList.add(userServer.getUserInfo(userServer.queryUserByName(likeUserName)));
        }
        model.addAttribute("likelist", likeUserInfoList);
        return new ModelAndView("likelist");
    }
}
