package com.baima.jianjia.mapper;

import com.baima.jianjia.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.baima.jianjia.pojo.UserInfo;

@Repository
@Mapper
public interface UserMapper {
	List<User> queryUserList();

	List<String> queryUserNameList();

	User queryUserByName(String name);

	void addUser(@Param("username") String username, @Param("password") String password);

	void updatePassword(@Param("username") String username,@Param("password") String password);
}
