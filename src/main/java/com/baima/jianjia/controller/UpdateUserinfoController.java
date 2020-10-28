package com.baima.jianjia.controller;


import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UpdateUserinfoController {
    @Autowired
    UserserviceImpl userService;
    @PostMapping(value = "updateuserinfo")
    public ModelAndView updateUserInfo(String nikename,int age, 
                                 String sex, String department,
                                 String key,String like,
                                 String constellation,
                                 String androphilia){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        userService.updateUserInfo(userinfo.username, age, nikename, sex, department, key, like,constellation,androphilia);
        return new ModelAndView("redirect:/userselfspace");
    }
}
