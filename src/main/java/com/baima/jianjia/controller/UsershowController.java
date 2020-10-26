package com.baima.jianjia.controller;

import java.util.ArrayList;
import java.util.List;
import com.baima.jianjia.pojo.Showcomment;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class UsershowController {
    public User getUserByBBS(){
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user;
    }
    public class userShowAndInfo{
        public Userinfo userinfo;
        public UserShow userShow;
    }
    public class showCommentAndInfo{
        public Showcomment showcomment;
        public Userinfo userinfo;
    }
    @Autowired
    UserserviceImpl userService;
    @PostMapping(value = "/postshow")
    public ModelAndView postShow(String showdata){
        User user = this.getUserByBBS();
        userService.postShow(user.username, showdata);
        return new ModelAndView("redirect:/userselfspace");
    }
    
    @RequestMapping(value = "/getallusershows")
    public ModelAndView getAllUserShows(Model model){
        List<UserShow> allUserShows = userService.getAllUserShows();
        List<userShowAndInfo> usershowandinfos = new ArrayList<>();
        for (UserShow userShow : allUserShows) {
            userShowAndInfo usershowandinfo = new userShowAndInfo();
            usershowandinfo.userShow = userShow;
            Userinfo userinfo = userService.getUserInfobyName(userShow.username);
            usershowandinfo.userinfo = userinfo;
            usershowandinfos.add(usershowandinfo);
        }
        model.addAttribute("usershowandinfos",usershowandinfos);
        return new ModelAndView("showlist");
    }
    
    @PostMapping(value = "/getshowcomments")
    public ModelAndView getShowcomments(int showid,Model model){
        List<Showcomment> showcomments =  userService.getShowcomments(showid);
        model.addAttribute("showComments", showcomments);
        model.addAttribute("showid", showid);
        return new ModelAndView("showcomments");
    }
    @PostMapping(value = "/postcomment")
    public ModelAndView postComment(int showid,String comment){
        User user = this.getUserByBBS();
        userService.postComment(user.username, comment, showid);
        return new ModelAndView("redirect:showinfo?showid="+showid);
    }
    @PostMapping(value = "/toshowlike")
    public String toShowLike(int showid){
        User user = this.getUserByBBS();
        if (user==null){
            return "{\"info\":\"点赞失败,未登录\",\"code\":1}";
        }
        if(userService.isShowLike(showid, user.username)){
            return "{\"info\":\"点赞失败,已点赞\",\"code\":1}";
        }
        userService.showLike(showid, user.username);
        return "{\"info\":\"点赞成功\",\"code\":0}";
    }
    @RequestMapping(value = "/showinfo")
    public ModelAndView getShowInfo(int showid,Model model){
        UserShow usershow = userService.getShowById(showid);
        if (usershow==null){
            return new ModelAndView("error/404");
        }
        Userinfo userinfo = userService.getUserInfobyName(usershow.username);
        List<Showcomment> showcomments =  userService.getShowcomments(showid);
        List<showCommentAndInfo> showCommentAndInfos = new ArrayList<>();
        for (Showcomment showcomment : showcomments) {
            showCommentAndInfo sCommentAndInfo = new showCommentAndInfo();
            sCommentAndInfo.showcomment = showcomment;
            sCommentAndInfo.userinfo = userService.getUserInfobyName(showcomment.username);
            showCommentAndInfos.add(sCommentAndInfo);
        }
        model.addAttribute("showcommentandinfos", showCommentAndInfos);
        model.addAttribute("postshowuserinfo", userinfo);
        model.addAttribute("usershow", usershow);
        return new ModelAndView("showinfo");
    }
}
