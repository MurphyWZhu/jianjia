package com.baima.jianjia.service;

import com.baima.jianjia.pojo.ShowComment;
import com.baima.jianjia.pojo.UserShow;

import java.util.List;

public interface UserShowService {
    void postShow(String username, String showdata);

    List<UserShow> getAllUserShows();

    List<UserShow> getSelfShows(String username);

    List<UserShow> getUserShows(String username);

    List<ShowComment> getShowcomments(int showid);

    void postComment(String username, String comment, int showid);

    void showLike(int showid, String username);

    UserShow getShowById(int showid);

    Boolean isShowLike(int showid,String username);
}
