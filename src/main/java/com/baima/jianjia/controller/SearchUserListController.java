package com.baima.jianjia.controller;

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
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class SearchUserListController {
    public List<Integer> myrange(int start,int end){
        List<Integer> pageList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            pageList.add(i);
        }
        return pageList;
    }

    @Autowired
    UserserviceImpl userService;
    @RequestMapping(value = "/search")
    public ModelAndView searchUserList(String key, String sexfilter, String departmentfilter,@RequestParam(defaultValue = "1") int page,Model model){
        String sexfilter0 = sexfilter;
        String departmentfilter0 = departmentfilter;
        if(sexfilter==null || departmentfilter==null){
            return new ModelAndView("search");
        }
        if(sexfilter.contains("不限")){
            sexfilter0="%";
        }
        if(departmentfilter.contains("不限")){
            departmentfilter0="%";
        }
        List<Userinfo> userinfoList = userService.searchUserinfoPage(key, sexfilter0, departmentfilter0, page);
        int maxPage = userService.countPage(key, sexfilter0, departmentfilter0);
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
        System.out.println(maxPage);
        
        List<Integer> pageList = new ArrayList<>();
        if(page>5){
            if(page+4<=maxPage){
                pageList = myrange(page-4, page+4);
            }else{
                pageList = myrange(maxPage-8, maxPage);
            }
        }else{
            if(maxPage>10){
                pageList = myrange(1, 10);
            }else{
                pageList = myrange(1, maxPage);
            }
            
        }

        model.addAttribute("maxpage", maxPage);
        model.addAttribute("userinfoList",userinfoList);
        model.addAttribute("searchkey",key);
        model.addAttribute("searchsex",sexfilter);
        model.addAttribute("searchdepartment",departmentfilter);
        model.addAttribute("page", page);
        model.addAttribute("pagelist", pageList);
        return new ModelAndView("search");
    }
}
