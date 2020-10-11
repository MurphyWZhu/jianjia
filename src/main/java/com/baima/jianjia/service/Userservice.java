package com.baima.jianjia.service;

import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;

public interface Userservice{
	public List<User> queryUserList();
	public List<String> queryUserNameList();
	public User queryUserByName(String name);
	public void addUser(User user);
	public Userinfo getUserInfo(User user);
	public List<Userinfo> searchKey(String key,String sexfilter,String departmentfilter);
	public List<Userinfo> searchNikeName(String nikename,String sexfilter,String departmentfilter);
	public void updateUserInfo(String username, int age, String nikename,
							   String sex, String department,
							   String profilepicture, String key,
							   String like);
	public List<String> getLikeList(String username);
	public void toLike(String username,String likeuser);
	public void rmLike(String username,String likeuser);
	public void postShow(String username,String showdata,Boolean ispublic);
	public List<UserShow> getAllUserShows();
	public List<UserShow> getSelfShows(String username);
	public List<UserShow> getUserShows(String username);
}
