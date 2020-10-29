package com.baima.jianjia.service;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.pojo.UserInfo;

import java.io.File;
import java.util.List;

public interface UserInfoService {
    UserInfo getUserInfo(User user);

    UserInfo getUserInfobyName(String username);

    List<UserInfo> searchKey(String key, String sexfilter, String departmentfilter);

    List<UserInfo> searchNikeName(String nikename, String sexfilter, String departmentfilter);

    void updateUserInfo(String username, int age, String nikename, String sex, String department, String key,
                               String like,String constellation,String androphilia);

    List<String> getLikeList(String username);

    void toLike(String username, String likeuser);

    void rmLike(String username, String likeuser);

    void updateUserpicture(File file, String username, String filetype);

    List<UserInfo> searchUserinfoPage(String key, String sexfilter, String departmentfilter, int page);

    int countPage(String key,String sexfilter,String departmentfilter);

    String getConstellationPairKey(String constellation,String pairconstellation);

    UserInfo pairUserByConstellation(String constellation, String sex, String androphilia);
}
