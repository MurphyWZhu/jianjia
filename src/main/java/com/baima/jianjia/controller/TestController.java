package com.baima.jianjia.controller;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.Userservice;
import com.baima.jianjia.service.UserserviceImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
	@Autowired
	UserserviceImpl userservice;
	@PostMapping(value = "/test")
	public List<Userinfo> searchUser(String key){
		List<Userinfo> userlist = userservice.searchKey(key);
		userlist.addAll(userservice.searchNikeName(key));
		return userlist;
	}
//	public Userinfo getInteger(){
//		Subject currentSubject = SecurityUtils.getSubject();
//		Session session = currentSubject.getSession();
//		User user = (User) session.getAttribute("loginUser");
//		if(user == null){
//			System.out.println("no login");
//			return null;
//		}
//		System.out.println(user.username);
//		return userservice.getUserInfo(user);
//}
}
