package com.baima.jianjia.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.baima.jianjia.pojo.Showcomment;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;
import com.baima.jianjia.pojo.pairConstellation;

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
			@Param("key") String key, @Param("like") String like,
			@Param("constellation") String constellation,@Param("androphilia") String androphilia);

	public List<String> getLikeList(String username);

	public void toLike(@Param("username") String username, @Param("likeuser") String likeuser);

	public void rmLike(@Param("username") String username, @Param("likeuser") String likeuser);

	public void postShow(@Param("username") String username, @Param("showdata") String showdata,
			@Param("timedate") String timedate);

	public List<UserShow> getAllUserShows();

	public List<UserShow> getSelfShows(String username);

	public List<UserShow> getUserShows(String username);

	public void updateUserPicture(@Param("username") String username, @Param("url") String url);

	public List<Showcomment> getShowcomments(int showid);

	public void postComment(@Param("username") String username, @Param("comment") String comment,
			@Param("showid") int showid, @Param("timedate") String timedate);

	public void showLike(@Param("showid") int showid, @Param("username") String username,
			@Param("timedate") String timedate);

	public UserShow getShowById(int showid);

	public int searchshowlike(@Param("showid") int showid, @Param("username") String username);

	public List<Userinfo> searchUserinfoPage(@Param("key") String key,@Param("sexfilter") String sexfilter, @Param("departmentfilter") String departmentfilter,
			@Param("page") int page);
	public int countPage(@Param("key") String key,@Param("sexfilter") String sexfilter, @Param("departmentfilter") String departmentfilter);

	public void updatePassword(@Param("username") String username,@Param("password") String password);

	public List<Userinfo> pairUserByConstellation(@Param("constellation") String constellation,@Param("pairsex") String pairsex);
	public List<pairConstellation> getPairConstellation(String constellation);
	public String getConstellationPairKey(@Param("constellation") String constellation,@Param("pairconstellation") String pairconstellation);
}
