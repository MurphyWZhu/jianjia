package com.baima.jianjia.controller;

import java.util.ArrayList;
import java.util.List;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;
import com.baima.jianjia.service.UserInfoServiceImpl;
import com.baima.jianjia.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class LikeController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    //获取用户喜欢的人列表的方法
    @RequestMapping(value="/userlike")
    public ModelAndView getLikeList(Model model) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        List<String> likeUserNameList = userInfoService.getLikeList(user.username);
        List<UserInfo> likeUserInfoList = new ArrayList<UserInfo>();
        for (String likeUserName : likeUserNameList) {
            UserInfo userinfo = userInfoService.getUserInfo(userService.queryUserByName(likeUserName));
            userinfo.likeit = true;
            likeUserInfoList.add(userinfo);
        }
        model.addAttribute("userinfoList", likeUserInfoList);
        return new ModelAndView("likelist");
    }
    //喜欢一个用户的方法
    @PostMapping(value = "/tolike")
    public String toLike(String likeuser){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        if(user==null){
            return null;
        }
        userInfoService.toLike(user.username, likeuser);
        return "{\"info\":\"喜欢成功\",\"code\":0}";
    }
    //取消喜欢一个用户的方法
    @PostMapping(value = "/rmlike")
    public String rmLike(String likeuser){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        if (user == null) {
            return null;
        }
        userInfoService.rmLike(user.username, likeuser);
        return "{\"info\":\"取消成功\",\"code\":0}";
    }
}
