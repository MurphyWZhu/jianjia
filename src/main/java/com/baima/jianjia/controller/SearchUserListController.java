package com.baima.jianjia.controller;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;
import com.baima.jianjia.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
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
    UserInfoService userInfoService;
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
        List<UserInfo> userInfoList = userInfoService.searchUserinfoPage(key, sexfilter0, departmentfilter0, page);
        int maxPage = userInfoService.countPage(key, sexfilter0, departmentfilter0);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        String username;
        if (user == null) {
            username = null;
            
        }else{
            username = user.username;
        }
        List<String> likeUserNameList = userInfoService.getLikeList(username);
        for (UserInfo userinfo : userInfoList) {
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
        model.addAttribute("userinfoList", userInfoList);
        model.addAttribute("searchkey",key);
        model.addAttribute("searchsex",sexfilter);
        model.addAttribute("searchdepartment",departmentfilter);
        model.addAttribute("page", page);
        model.addAttribute("pagelist", pageList);
        return new ModelAndView("search");
    }
}
