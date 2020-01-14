package com.changgou.controller;
/*
 * @author April0ne
 * @date 2020/1/14 15:27
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orderweb")
@CrossOrigin
public class OrderController {


    @GetMapping("list")
    public String list(){
        return "myOrder";
    }
}
