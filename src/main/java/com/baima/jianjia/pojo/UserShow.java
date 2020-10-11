package com.baima.jianjia.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShow {
    public String username;
    public String showdata;
    public Boolean ispublic;
    public String timedate;
}
