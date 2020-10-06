package com.baima.jianjia.service;

import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;

public interface Userservice{
	public List<User> queryUserList();
	public List<String> queryUserNameList();
	public User queryUserByName(String name);
	public void addUser(User user);
	public Userinfo getUserInfo(User user);
	public List<Userinfo> searchKey(String key);
	public List<Userinfo> searchNikeName(String nikename);
	public void updateUserInfo(String username, int age, String nikename,
							   String sex, String department,
							   String profilepicture, String key,
							   String like);
}
