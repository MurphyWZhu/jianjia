package com.baima.jianjia.mapper;

import com.baima.jianjia.pojo.PairConstellation;
import com.baima.jianjia.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserInfoMapper {
    UserInfo getUserInfo(String username);

    List<UserInfo> searchKey(@Param("key") String key, @Param("sexfilter") String sexfilter,
                                    @Param("departmentfilter") String departmentfilter);

    List<UserInfo> searchNikeName(@Param("nikename") String nikename, @Param("sexfilter") String sexfilter,
                                         @Param("departmentfilter") String departmentfilter);

    void updateUserInfo(@Param("username") String username, @Param("age") int age,
                               @Param("nikename") String nikename, @Param("sex") String sex, @Param("department") String department,
                               @Param("key") String key, @Param("like") String like,
                               @Param("constellation") String constellation,@Param("androphilia") String androphilia);

    List<String> getLikeList(String username);

    void toLike(@Param("username") String username, @Param("likeuser") String likeuser);

    void rmLike(@Param("username") String username, @Param("likeuser") String likeuser);

    void updateUserPicture(@Param("username") String username, @Param("url") String url);

    List<UserInfo> searchUserinfoPage(@Param("key") String key, @Param("sexfilter") String sexfilter, @Param("departmentfilter") String departmentfilter,
                                      @Param("page") int page);
    int countPage(@Param("key") String key,@Param("sexfilter") String sexfilter, @Param("departmentfilter") String departmentfilter);

    List<UserInfo> pairUserByConstellation(@Param("constellation") String constellation, @Param("pairsex") String pairsex);
    List<PairConstellation> getPairConstellation(String constellation);
    String getConstellationPairKey(@Param("constellation") String constellation,@Param("pairconstellation") String pairconstellation);
}
