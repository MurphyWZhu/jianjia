package com.baima.jianjia.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public String username;
    public String password;
    public String getPwd() {
        return this.password;
    }
}
