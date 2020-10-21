package com.baima.jianjia.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.baima.jianjia.pojo.Showcomment;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;

@Repository
@Mapper
public interface UserMapper {
	List<User> queryUserList();

	List<String> queryUserNameList();

	public User queryUserByName(String name);

	String getusernameByid(int id);

	public void addUser(@Param("username") String username, @Param("password") String password);

	public Userinfo getUserInfo(String username);

	public List<Userinfo> searchKey(@Param("key") String key, @Param("sexfilter") String sexfilter,
			@Param("departmentfilter") String departmentfilter);

	public List<Userinfo> searchNikeName(@Param("nikename") String nikename, @Param("sexfilter") String sexfilter,
			@Param("departmentfilter") String departmentfilter);

	public void updateUserInfo(@Param("username") String username, @Param("age") int age,
			@Param("nikename") String nikename, @Param("sex") String sex, @Param("department") String department,
			@Param("profilepicture") String profilepicture, @Param("key") String key, @Param("like") String like);

	public List<String> getLikeList(String username);

	public void toLike(@Param("username") String username, @Param("likeuser") String likeuser);

	public void rmLike(@Param("username") String username, @Param("likeuser") String likeuser);

	public void postShow(@Param("username") String username, @Param("showdata") String showdata,
			@Param("ispublic") Boolean ispublic, @Param("timedate") String timedate);

	public List<UserShow> getAllUserShows();

	public List<UserShow> getSelfShows(String username);

	public List<UserShow> getUserShows(String username);

	public void updateUserPicture(@Param("username") String username, @Param("url") String url);

	public List<Showcomment> getShowcomments(int showid);

	public void postComment(@Param("username") String username, @Param("comment") String comment,
			@Param("showid") int showid, @Param("timedate") String timedate);
}
