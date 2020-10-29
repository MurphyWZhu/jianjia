package com.baima.jianjia.service;

import com.baima.jianjia.mapper.UserShowMapper;
import com.baima.jianjia.pojo.ShowComment;
import com.baima.jianjia.pojo.UserShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserShowServiceImpl implements UserShowService{
    @Autowired
    UserShowMapper userShowMapper;

    @Override
    public void postShow(String username, String showdata) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timedate = simpleDateFormat.format(date);
        userShowMapper.postShow(username, showdata, timedate);
    }

    @Override
    public List<UserShow> getAllUserShows() {
        return userShowMapper.getAllUserShows();
    }

    @Override
    public List<UserShow> getUserShows(String username) {
        return userShowMapper.getUserShows(username);
    }

    @Override
    public List<UserShow> getSelfShows(String username) {
        return userShowMapper.getSelfShows(username);
    }

    @Override
    public List<ShowComment> getShowcomments(int showid) {
        return userShowMapper.getShowcomments(showid);
    }

    @Override
    public void postComment(String username, String comment, int showid) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timedate = simpleDateFormat.format(date);
        userShowMapper.postComment(username, comment, showid, timedate);
    }

    @Override
    public void showLike(int showid, String username) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timedate = simpleDateFormat.format(date);
        userShowMapper.showLike(showid, username, timedate);
    }

    @Override
    public UserShow getShowById(int showid) {
        return userShowMapper.getShowById(showid);
    }

    @Override
    public Boolean isShowLike(int showid,String username){
        return userShowMapper.searchshowlike(showid, username)!=0;
    }
}
