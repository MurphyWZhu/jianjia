package com.baima.jianjia.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    public String username;
    public int age;
    public String nikename;
    public String sex;
    public String androphilia;
    public String constellation;
    public String department;
    public String profilepicture;
    public String key;
    public String like;
    public boolean likeit;
    public String motto;
}
