package com.baima.jianjia.controller;
/*
* 用于用户注册
* */

import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.service.UserServiceImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SignupController{
	@Autowired
	UserServiceImpl userService;
	@PostMapping(value = "/tosignup")
	public String login(String username,String password){
		User user = new User();
		user.username = username;
		user.password = password;
		List<String> usernameList = userService.queryUserNameList();
		if(usernameList.contains(username)){
			return "{\"info\":\"用户名已被注册\",\"code\":1}";
		}
		userService.addUser(user);
		return "{\"info\":\"注册成功\",\"code\":0}";
	}
	@PostMapping(value = "/updatepassword")
	public ModelAndView updatePassword(String password){
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
		userService.updatePassword(user.username, password);
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new ModelAndView("login");
	}
}
