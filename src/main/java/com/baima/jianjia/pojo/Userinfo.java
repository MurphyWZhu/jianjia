package com.baima.jianjia.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userinfo {
    public String username;
    public int age;
    public String address;
    public String nikename;
    public String sex;
    public String job;
    public String phonenumber;
    public String profilepicture;
    public String key;
}
