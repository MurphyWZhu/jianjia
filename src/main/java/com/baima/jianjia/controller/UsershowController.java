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
    public class userShowandInfo{
        public String username;
        public String nikename;
        public String profilepicture;
        public String sex;
        public String department;
        public String showdata;
        public String timedate;
        public int showid;
    }
    @Autowired
    UserserviceImpl userService;
    @PostMapping(value = "/postshow")
    public String postShow(String showdata,Boolean ispublic){
        User user = this.getUserByBBS();
        userService.postShow(user.username, showdata, ispublic);
        return "hello";
    }
    @RequestMapping("/postshowbox")
    public ModelAndView postShowBox(){
        return new ModelAndView("postshowbox");
    }
    @RequestMapping(value = "/getallusershows")
    public ModelAndView getAllUserShows(Model model){
        List<UserShow> allUserShows = userService.getAllUserShows();
        List<userShowandInfo> uShowandInfos = new ArrayList<>();
        for (UserShow userShow : allUserShows) {
            userShowandInfo uShowandInfo = new userShowandInfo();
            uShowandInfo.username=userShow.username;
            uShowandInfo.showdata=userShow.showdata;
            uShowandInfo.timedate=userShow.timedate;
            uShowandInfo.showid=userShow.id;
            Userinfo userinfo = userService.getUserInfobyName(userShow.username);
            uShowandInfo.nikename=userinfo.nikename;
            uShowandInfo.profilepicture=userinfo.profilepicture;
            uShowandInfo.department=userinfo.department;
            uShowandInfo.sex=userinfo.sex;
            uShowandInfos.add(uShowandInfo);
        }
        model.addAttribute("uShowandInfos",uShowandInfos);
        return new ModelAndView("showlist");
    }
    @PostMapping(value = "/getselfshows")
    public ModelAndView getSelfShows(Model model){
        User user = this.getUserByBBS();
        String username = user.username;
        Userinfo userinfo = userService.getUserInfo(user);
        List<UserShow> userShows = userService.getSelfShows(username);
        model.addAttribute("userShows", userShows);
        model.addAttribute("userinfo", userinfo);
        return new ModelAndView("subselfshow");
    }
    @PostMapping(value = "/getusershows")
    public ModelAndView getUserShows(String username,Model model){
        return new ModelAndView("subusershow");
    }
    @PostMapping(value = "/getshowcomments")
    public ModelAndView getShowcomments(int showid,Model model){
        List<Showcomment> showcomments =  userService.getShowcomments(showid);
        model.addAttribute("showComments", showcomments);
        model.addAttribute("showid", showid);
        return new ModelAndView("showcomments");
    }
    @PostMapping(value = "/postcomment")
    public String postComment(int showid,String comment){
        User user = this.getUserByBBS();
        userService.postComment(user.username, comment, showid);
        return "hello";
    }
    @PostMapping(value = "/toshowlike")
    public String toShowLike(int showid){
        User user = this.getUserByBBS();
        if (user==null){
            return "{\"info\":\"点赞失败\",\"code\":1}";
        }
        userService.showLike(showid, user.username);
        return "{\"info\":\"点赞成功成功\",\"code\":0}";
    }
    @RequestMapping(value = "/showinfo")
    public ModelAndView getShowInfo(int showid,Model model){
        UserShow usershow = userService.getShowById(showid);
        if (usershow==null){
            return new ModelAndView("error/404");
        }
        Userinfo userinfo = userService.getUserInfobyName(usershow.username);
        List<Showcomment> showcomments =  userService.getShowcomments(showid);
        model.addAttribute("showComments", showcomments);
        model.addAttribute("postshowuserinfo", userinfo);
        model.addAttribute("usershow", usershow);
        return new ModelAndView("showinfo");
    }
}
