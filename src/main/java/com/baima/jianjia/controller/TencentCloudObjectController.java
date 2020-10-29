package com.baima.jianjia.controller;

import java.io.File;
import java.io.IOException;

import com.baima.jianjia.pojo.User;
import com.baima.jianjia.service.UserInfoServiceImpl;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TencentCloudObjectController {
    @Autowired
    UserInfoServiceImpl userInfoService;

    @PostMapping(value = "/updateprofilepicture")
    public ModelAndView updateProfilePicture(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ModelAndView("redirect:/userselfspace");
        }
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");
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
            return new ModelAndView("redirect:/userselfspace");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ModelAndView("redirect:/userselfspace");
        }
        userInfoService.updateUserpicture(dest, user.username,filetype);
        return new ModelAndView("redirect:/userselfspace");
    }
}
