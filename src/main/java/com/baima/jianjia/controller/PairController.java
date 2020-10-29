package com.baima.jianjia.controller;

import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;
import com.baima.jianjia.service.UserInfoServiceImpl;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PairController {
    @Autowired
    UserInfoServiceImpl userInfoService;
    
    @RequestMapping("/pairuser")
    public ModelAndView pairUser(Model model){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        UserInfo userinfo = userInfoService.getUserInfo(user);
        UserInfo pairuserinfo =  userInfoService.pairUserByConstellation(userinfo.constellation,userinfo.sex,userinfo.androphilia);
        String pairKeyConstellation = userInfoService.getConstellationPairKey(userinfo.constellation, pairuserinfo.constellation);
        List<String> likeUserNameList = userInfoService.getLikeList(userinfo.username);
        pairuserinfo.likeit = likeUserNameList.contains(pairuserinfo.username);
        System.out.println(userinfo.constellation+pairuserinfo.constellation+pairKeyConstellation);
        model.addAttribute("userinfo", userinfo);
        model.addAttribute("pairuserinfo", pairuserinfo);
        model.addAttribute("pairkeyconstellation", pairKeyConstellation);
        return new ModelAndView("pair");
    }
}
