package com.baima.jianjia.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.Userinfo;
@Repository
@Mapper
public interface UserMapper{
	List<User> queryUserList();
	List<String> queryUserNameList();
	public User queryUserByName(String name);
	String getusernameByid(int id);
	public void addUser(@Param("username")String username,@Param("password")String password);
	public Userinfo getUserInfo(String username);
	public List<Userinfo> searchKey(String key);
	public List<Userinfo> searchNikeName(String nikename);
}
