package com.baima.jianjia.controller;

import java.util.ArrayList;
import java.util.List;

import com.baima.jianjia.pojo.ShowComment;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.service.UserInfoService;
import com.baima.jianjia.service.UserShowServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class UsershowController {
    public class userShowAndInfo{
        public UserInfo userinfo;
        public UserShow userShow;
    }
    public class showCommentAndInfo{
        public ShowComment showcomment;
        public UserInfo userinfo;
    }
    @Autowired
    UserShowServiceImpl userShowService;
    @Autowired
    UserInfoService userInfoService;
    @PostMapping(value = "/postshow")
    public ModelAndView postShow(String showdata){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        userShowService.postShow(user.username, showdata);
        return new ModelAndView("redirect:/userselfspace");
    }
    
    @RequestMapping(value = "/getallusershows")
    public ModelAndView getAllUserShows(Model model){
        List<UserShow> allUserShows = userShowService.getAllUserShows();
        List<userShowAndInfo> usershowandinfos = new ArrayList<>();
        for (UserShow userShow : allUserShows) {
            userShowAndInfo usershowandinfo = new userShowAndInfo();
            usershowandinfo.userShow = userShow;
            UserInfo userinfo = userInfoService.getUserInfobyName(userShow.username);
            usershowandinfo.userinfo = userinfo;
            usershowandinfos.add(usershowandinfo);
        }
        model.addAttribute("usershowandinfos",usershowandinfos);
        return new ModelAndView("showlist");
    }
    
    @PostMapping(value = "/getshowcomments")
    public ModelAndView getShowcomments(int showid,Model model){
        List<ShowComment> showComments =  userShowService.getShowcomments(showid);
        model.addAttribute("showComments", showComments);
        model.addAttribute("showid", showid);
        return new ModelAndView("showcomments");
    }
    @PostMapping(value = "/postcomment")
    public ModelAndView postComment(int showid,String comment){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        userShowService.postComment(user.username, comment, showid);
        return new ModelAndView("redirect:showinfo?showid="+showid);
    }
    @PostMapping(value = "/toshowlike")
    public String toShowLike(int showid){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
        if (user==null){
            return "{\"info\":\"点赞失败,未登录\",\"code\":1}";
        }
        if(userShowService.isShowLike(showid, user.username)){
            return "{\"info\":\"点赞失败,已点赞\",\"code\":1}";
        }
        userShowService.showLike(showid, user.username);
        return "{\"info\":\"点赞成功\",\"code\":0}";
    }
    @RequestMapping(value = "/showinfo")
    public ModelAndView getShowInfo(int showid,Model model){
        UserShow usershow = userShowService.getShowById(showid);
        if (usershow==null){
            return new ModelAndView("error/404");
        }
        UserInfo userinfo = userInfoService.getUserInfobyName(usershow.username);
        List<ShowComment> showComments =  userShowService.getShowcomments(showid);
        List<showCommentAndInfo> showCommentAndInfos = new ArrayList<>();
        for (ShowComment showcomment : showComments) {
            showCommentAndInfo sCommentAndInfo = new showCommentAndInfo();
            sCommentAndInfo.showcomment = showcomment;
            sCommentAndInfo.userinfo = userInfoService.getUserInfobyName(showcomment.username);
            showCommentAndInfos.add(sCommentAndInfo);
        }
        model.addAttribute("showcommentandinfos", showCommentAndInfos);
        model.addAttribute("postshowuserinfo", userinfo);
        model.addAttribute("usershow", usershow);
        return new ModelAndView("showinfo");
    }
}
