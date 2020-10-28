package com.baima.jianjia.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PairController {
    public User getUserByBBS(){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user;
    }
    @Autowired
    UserserviceImpl userService;
    
    @RequestMapping("/pairuser")
    public ModelAndView pairUser(Model model){
        Userinfo userinfo = userService.getUserInfo(getUserByBBS());
        Userinfo pairuserinfo =  userService.pairUserByConstellation(userinfo.constellation,userinfo.sex,userinfo.androphilia);
        String pairKeyConstellation = userService.getConstellationPairKey(userinfo.constellation, pairuserinfo.constellation);
        List<String> likeUserNameList = userService.getLikeList(userinfo.username);
        pairuserinfo.likeit = likeUserNameList.contains(pairuserinfo.username);
        System.out.println(userinfo.constellation+pairuserinfo.constellation+pairKeyConstellation);
        model.addAttribute("userinfo", userinfo);
        model.addAttribute("pairuserinfo", pairuserinfo);
        model.addAttribute("pairkeyconstellation", pairKeyConstellation);
        return new ModelAndView("pair");
    }
}
