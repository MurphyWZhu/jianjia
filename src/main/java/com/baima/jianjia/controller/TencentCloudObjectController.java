package com.baima.jianjia.controller;

import java.io.File;
import java.io.IOException;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.service.UserserviceImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TencentCloudObjectController {
    @Autowired
    UserserviceImpl userService;

    @PostMapping(value = "/updateprofilepicture")
    public String updateProfilePicture(@RequestParam("file")MultipartFile file) throws IllegalStateException, IOException {
        if(file.isEmpty()){
            return "error";
        }
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String filetype = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(filetype);
        File dest = new File("/opt/"+"tmp.jepg");
        file.transferTo(dest);
        userService.updateUserpicture(dest, user.username,filetype);
        return "hello";
    }
}
