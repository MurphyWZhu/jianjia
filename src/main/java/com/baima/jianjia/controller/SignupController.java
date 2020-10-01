package com.baima.jianjia.controller;


import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.service.UserserviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController{
	@Autowired
	UserserviceImpl userservice;
	@PostMapping(value = "/tosignup")
	public String login(String username,String password){
		User user = new User();
		user.username = username;
		user.password = password;
		List<String> usernameList = userservice.queryUserNameList();
		if(usernameList.contains(username)){
			return "{\"info\":\"用户名已被注册\",\"code\":1}";
		}
		userservice.addUser(user);
		return "{\"info\":\"注册成功\",\"code\":0}";
		
	}

}
