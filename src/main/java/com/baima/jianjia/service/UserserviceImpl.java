package com.baima.jianjia.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.baima.jianjia.mapper.UserMapper;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserserviceImpl implements Userservice{
	@Autowired
	UserMapper userMapper;
	@Override
	public List<User> queryUserList(){
		return userMapper.queryUserList();
	}

	@Override
	public List<String> queryUserNameList(){
		return userMapper.queryUserNameList();
	}
	@Override
	public User queryUserByName(String name){
		return userMapper.queryUserByName(name);
	}
	@Override
	public void addUser(User user){
		userMapper.addUser(user.username, user.password);
	}
	@Override
	public Userinfo getUserInfo(User user){
		return userMapper.getUserInfo(user.username);
	}
	@Override
	public List<Userinfo> searchKey(String key,String sexfilter,String departmentfilter){
		key="%"+key+"%";
		return userMapper.searchKey(key,sexfilter,departmentfilter);
	}
	public List<Userinfo> searchNikeName(String nikename,String sexfilter,String departmentfilter){
		nikename="%"+nikename+"%";
		return userMapper.searchNikeName(nikename,sexfilter,departmentfilter);
	}
	public void updateUserInfo(String username, int age, String nikename,
							   String sex, String department,
							   String profilepicture, String key,
							   String like){
		userMapper.updateUserInfo(username, age, nikename, sex, department, profilepicture, key, like);
	}
	public List<String> getLikeList(String username){
		return userMapper.getLikeList(username);
	}
	public void toLike(String username,String likeuser){
		userMapper.toLike(username, likeuser);
	}
	public void rmLike(String username,String likeuser){
		userMapper.rmLike(username, likeuser);
	}
	public void postShow(String username, String showdata, Boolean ispublic) {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timedate = simpleDateFormat.format(date);
		userMapper.postShow(username, showdata, ispublic, timedate);
	}
	public List<UserShow> getAllUserShows(){
		return userMapper.getAllUserShows();
	}
	public List<UserShow> getUserShows(String username){
		return userMapper.getUserShows(username);
	}
	public List<UserShow> getSelfShows(String username){
		return userMapper.getSelfShows(username);
	}
}
