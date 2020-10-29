package com.baima.jianjia.controller;


import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;
import com.baima.jianjia.service.UserInfoServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UpdateUserinfoController {
    @Autowired
    UserInfoServiceImpl userInfoService;
    @PostMapping(value = "updateuserinfo")
    public ModelAndView updateUserInfo(String nikename,int age, 
                                 String sex, String department,
                                 String key,String like,
                                 String constellation,
                                 String androphilia){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        UserInfo userinfo = userInfoService.getUserInfo(user);
        userInfoService.updateUserInfo(userinfo.username, age, nikename, sex, department, key, like,constellation,androphilia);
        return new ModelAndView("redirect:/userselfspace");
    }
}
