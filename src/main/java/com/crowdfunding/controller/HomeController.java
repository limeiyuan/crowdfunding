package com.crowdfunding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LiMeiyuan on 2016/12/25.
 * Description:
 */
@Controller
@RequestMapping("home")
public class HomeController {
    @RequestMapping("index")
    public String index() {
        return "home";
    }
}
