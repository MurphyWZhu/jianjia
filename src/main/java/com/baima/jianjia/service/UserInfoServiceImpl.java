package com.baima.jianjia.service;

import com.baima.jianjia.mapper.UserInfoMapper;
import com.baima.jianjia.pojo.PairConstellation;
import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public UserInfo getUserInfo(User user) {
        return userInfoMapper.getUserInfo(user.username);
    }

    @Override
    public UserInfo getUserInfobyName(String username) {
        return userInfoMapper.getUserInfo(username);
    }

    @Override
    public List<UserInfo> searchKey(String key, String sexfilter, String departmentfilter) {
        key = "%" + key + "%";
        return userInfoMapper.searchKey(key, sexfilter, departmentfilter);
    }

    @Override
    public List<UserInfo> searchNikeName(String nikename, String sexfilter, String departmentfilter) {
        nikename = "%" + nikename + "%";
        return userInfoMapper.searchNikeName(nikename, sexfilter, departmentfilter);
    }

    @Override
    public void updateUserInfo(String username, int age, String nikename, String sex, String department, String key,
                               String like,String constellation,String androphilia) {
        userInfoMapper.updateUserInfo(username, age, nikename, sex, department, key, like, constellation, androphilia);
    }

    @Override
    public List<String> getLikeList(String username) {
        return userInfoMapper.getLikeList(username);
    }

    @Override
    public void toLike(String username, String likeuser) {
        userInfoMapper.toLike(username, likeuser);
    }

    @Override
    public void rmLike(String username, String likeuser) {
        userInfoMapper.rmLike(username, likeuser);
    }

    @Override
    public void updateUserpicture(File file, String username, String filetype) {
        String secretId = "AKIDBXfZCZFQxFZgaDcx0meNSFkOoxoeRcuw";
        String secretKey = "1mLlE8pHRm49xW4QWKy82g5WqtfdMozx";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照
        // https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题
        // Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要上传的文件
        File localFile = file;
        // 指定要上传到的存储桶
        String bucketName = "jianjia-1253347887";
        // 指定要上传到 COS 上对象键
        String key = UUID.randomUUID().toString() + filetype;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
        userInfoMapper.updateUserPicture(username, "https://jianjia-1253347887.cos.ap-nanjing.myqcloud.com/" + key);
    }

    @Override
    public List<UserInfo> searchUserinfoPage(String key, String sexfilter, String departmentfilter, int page){
        key = "%" + key + "%";
        System.out.println(sexfilter+":"+departmentfilter);
        page = (page-1)*10;
        return userInfoMapper.searchUserinfoPage(key, sexfilter, departmentfilter, page);
    }

    @Override
    public int countPage(String key,String sexfilter,String departmentfilter){
        key = "%" + key + "%";
        return userInfoMapper.countPage(key, sexfilter, departmentfilter)/10+1;
    }

    @Override
    public String getConstellationPairKey(String constellation,String pairconstellation){
        return userInfoMapper.getConstellationPairKey(constellation,pairconstellation);
    }

    @Override
    public UserInfo pairUserByConstellation(String constellation, String sex, String androphilia){
        String pairsex = new String();
        if(androphilia.contains("同性")){
            pairsex = sex;
        }else{
            if(sex.contains("男")){
                pairsex="女";
            }else{
                pairsex="男";
            }
        }
        Random rd = new Random(System.currentTimeMillis());
        List<PairConstellation> pairConstellations = userInfoMapper.getPairConstellation(constellation);
        List<UserInfo> userinfos0 = userInfoMapper.pairUserByConstellation(pairConstellations.get(0).pairconstellation,pairsex);
        int userinfocount0 = userinfos0.size();
        List<UserInfo> userinfos1 = userInfoMapper.pairUserByConstellation(pairConstellations.get(1).pairconstellation,pairsex);
        int userinfocount1 = userinfos1.size();
        int rdnum = rd.nextInt(userinfocount0+userinfocount1);
        System.out.println(rdnum);
        System.out.println(userinfocount0);
        System.out.println(userinfocount1);
        if(rdnum>userinfocount0-1){
            return userinfos1.get(rdnum-userinfocount0);
        }else{
            return userinfos0.get(rdnum);
        }
    }
}
