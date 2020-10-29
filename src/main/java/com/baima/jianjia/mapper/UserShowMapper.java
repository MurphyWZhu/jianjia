package com.baima.jianjia.mapper;

import com.baima.jianjia.pojo.ShowComment;
import com.baima.jianjia.pojo.UserShow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserShowMapper {
    void postShow(@Param("username") String username, @Param("showdata") String showdata,
                         @Param("timedate") String timedate);

    List<UserShow> getAllUserShows();

    List<UserShow> getSelfShows(String username);

    List<UserShow> getUserShows(String username);

    List<ShowComment> getShowcomments(int showid);
    void postComment(@Param("username") String username, @Param("comment") String comment,
                     @Param("showid") int showid, @Param("timedate") String timedate);

    void showLike(@Param("showid") int showid, @Param("username") String username,
                         @Param("timedate") String timedate);

    UserShow getShowById(int showid);

    int searchshowlike(@Param("showid") int showid, @Param("username") String username);
}
