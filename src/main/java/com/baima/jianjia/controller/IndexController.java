package com.baima.jianjia.controller;
/*
* 用于首页的控制器
*/
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
    //定义首页地址
    @RequestMapping(value = {"/","/index"})
    public ModelAndView IndexPage() {
        return new ModelAndView("index");
    }
}
