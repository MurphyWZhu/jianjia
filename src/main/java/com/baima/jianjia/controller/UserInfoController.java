package com.baima.jianjia.controller;


import com.baima.jianjia.pojo.User;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserInfoController{
	@RequestMapping(value = "/getuserinfo")
	public String getUserinfo(){
		Subject currentSubject = SecurityUtils.getSubject();
		Session session = currentSubject.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (user == null) {
			return "{\"info\":\"未登录\",\"code\":1}";
		}else {
			return "{\"info\":\""+user.username+"\",\"code\":0}";
		}
	}
	@RequestMapping("/userinfo")
	public ModelAndView userInfo(){
		return new ModelAndView("userinfo");
	}
}
