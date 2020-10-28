package com.baima.jianjia.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.baima.jianjia.pojo.Showcomment;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;

public interface Userservice {
	public List<User> queryUserList();

	public List<String> queryUserNameList();

	public User queryUserByName(String name);

	public void addUser(User user);

	public Userinfo getUserInfo(User user);

	public Userinfo getUserInfobyName(String username);

	public List<Userinfo> searchKey(String key, String sexfilter, String departmentfilter);

	public List<Userinfo> searchNikeName(String nikename, String sexfilter, String departmentfilter);

	public void updateUserInfo(String username, int age, String nikename, String sex, String department, String key,
			String like,String constellation,String androphilia);

	public List<String> getLikeList(String username);

	public void toLike(String username, String likeuser);

	public void rmLike(String username, String likeuser);

	public void postShow(String username, String showdata);

	public List<UserShow> getAllUserShows();

	public List<UserShow> getSelfShows(String username);

	public List<UserShow> getUserShows(String username);

	public void updateUserpicture(File file, String username, String filetype);

	public List<Showcomment> getShowcomments(int showid);

	public void postComment(String username, String comment, int showid);

	public void showLike(int showid, String username);

	public UserShow getShowById(int showid);
	public Boolean isShowLike(int showid,String username);
	public List<Userinfo> searchUserinfoPage(String key,String sexfilter,String departmentfilter,int page);
	public int countPage(String key,String sexfilter,String departmentfilter);

	public void updatePassword(String username,String password);
	public String getConstellationPairKey(String constellation,String pairconstellation);
	public Userinfo pairUserByConstellation(String constellation,String sex,String androphilia);
}
