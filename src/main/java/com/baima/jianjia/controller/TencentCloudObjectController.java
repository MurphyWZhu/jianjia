package com.baima.jianjia.controller;

import java.io.File;
import java.io.IOException;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.service.UserserviceImpl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
    public String updateProfilePicture(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "error";
        }
        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        User user = (User) session.getAttribute("loginUser");
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        String filetype = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(filetype);
        File dest = new File("/opt/" + "tmp.jepg");
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "{\"info\":\"修改失败\",\"code\":1}";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "{\"info\":\"修改失败\",\"code\":1}";
        }
        userService.updateUserpicture(dest, user.username,filetype);
        return "{\"info\":\"修改成功\",\"code\":0}";
    }
}
