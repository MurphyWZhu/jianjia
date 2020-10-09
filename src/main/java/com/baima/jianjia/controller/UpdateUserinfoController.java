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

@RestController
public class UpdateUserinfoController {
    @Autowired
    UserserviceImpl userService;
    @PostMapping(value = "updateuserinfo")
    public String updateUserInfo(int age, String nikename,
                                 String sex, String department,
                                 String profilepicture, String key,
                                 String like){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("no login");
            return null;
        }
        Userinfo userinfo = userService.getUserInfo(user);
        userService.updateUserInfo(userinfo.username, age, nikename, sex, department, profilepicture, key, like);
        return "{\"info\":\"修改成功\",\"code\":0}";
    }
}
