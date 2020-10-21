package com.baima.jianjia.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showcomment {
    public int commentid;
    public int showid;
    public String comment;
    public String username;
    public String timedate;
}
