package com.baima.jianjia.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.baima.jianjia.mapper.UserMapper;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserShow;
import com.baima.jianjia.pojo.Userinfo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

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
	public Userinfo getUserInfobyName(String username){
		return userMapper.getUserInfo(username);
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
	public void updateUserpicture(File file,String username,String filetype){
		String secretId = "AKIDBXfZCZFQxFZgaDcx0meNSFkOoxoeRcuw";
		String secretKey = "1mLlE8pHRm49xW4QWKy82g5WqtfdMozx";
		COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
		// 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
		// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
		Region region = new Region("ap-nanjing");
		ClientConfig clientConfig = new ClientConfig(region);
		// 3 生成 cos 客户端。
		COSClient cosClient = new COSClient(cred, clientConfig);
		// 指定要上传的文件
		File localFile = file;
		// 指定要上传到的存储桶
		String bucketName = "jianjia-1253347887";
		// 指定要上传到 COS 上对象键
		String key = UUID.randomUUID().toString()+filetype;
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
		PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
		cosClient.shutdown();
		userMapper.updateUserPicture(username, "https://jianjia-1253347887.cos.ap-nanjing.myqcloud.com/"+key);
	}
}
