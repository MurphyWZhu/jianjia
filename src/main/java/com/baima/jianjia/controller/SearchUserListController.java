package com.baima.jianjia.controller;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class SearchUserListController {
    @Autowired
    UserserviceImpl userService;
    @PostMapping(value = "/tosearch")
    public ModelAndView searchUserList(String key, String sexfilter, String departmentfilter,Model model){
        if(sexfilter.contains("不限")){
            sexfilter="%";
        }
        if(departmentfilter.contains("不限")){
            departmentfilter="%";
        }
        System.out.println(sexfilter+":"+departmentfilter);
        List<Userinfo> userinfoList = userService.searchKey(key,sexfilter,departmentfilter);
        List<String> userinfoListName = new ArrayList<>();
        for (Userinfo userinfo : userinfoList) {
            userinfoListName.add(userinfo.username);
        }
        List<Userinfo> userinfoListByNikeName = userService.searchNikeName(key,sexfilter,departmentfilter);
        for (Userinfo userinfo : userinfoListByNikeName) {
            if(!userinfoListName.contains(userinfo.username)){
                userinfoList.add(userinfo);
            }
        }
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
        return new ModelAndView("subsearch");
    }
    @RequestMapping(value="/search", method=RequestMethod.GET)
    public ModelAndView searchpage() {
        return new ModelAndView("search");
    }
    
}
