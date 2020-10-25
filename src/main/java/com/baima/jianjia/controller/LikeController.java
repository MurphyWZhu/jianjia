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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class LikeController {
    //次方法用于获取session中的用户信息
    public User getUserByBBS(){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user;
    }
    //自动装载UserserviceImpl
    @Autowired
    UserserviceImpl userServer;
    //获取用户喜欢的人列表的方法
    @RequestMapping(value="/userlike")
    public ModelAndView getLikeList(Model model) {
        User user = this.getUserByBBS();
        List<String> likeUserNameList = userServer.getLikeList(user.username);
        List<Userinfo> likeUserInfoList = new ArrayList<Userinfo>();
        for (String likeUserName : likeUserNameList) {
            Userinfo userinfo = userServer.getUserInfo(userServer.queryUserByName(likeUserName));
            userinfo.likeit = true;
            likeUserInfoList.add(userinfo);
        }
        model.addAttribute("userinfoList", likeUserInfoList);
        return new ModelAndView("likelist");
    }
    //喜欢一个用户的方法
    @PostMapping(value = "/tolike")
    public String toLike(String likeuser){
        User user = this.getUserByBBS();
        if(user==null){
            return null;
        }
        userServer.toLike(user.username, likeuser);
        return "{\"info\":\"喜欢成功\",\"code\":0}";
    }
    //取消喜欢一个用户的方法
    @PostMapping(value = "/rmlike")
    public String rmLike(String likeuser){
        User user = this.getUserByBBS();
        if (user == null) {
            return null;
        }
        userServer.rmLike(user.username, likeuser);
        return "{\"info\":\"取消成功\",\"code\":0}";
    }
}
