package com.baima.jianjia.controller;

import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.service.UserserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
	@Autowired
	UserserviceImpl userService;
	@PostMapping(value = "/test")
	public List<Userinfo> searchUser(String key){
		List<Userinfo> userList = userService.searchKey(key);
		userList.addAll(userService.searchNikeName(key));
		return userList;
	}
}
