package com.example.demoWeb.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {


    @RequestMapping("/")
//    @ResponseBody                          /cz we have putten @RestController
    public String greeting(){
        return "Hello World!";
    }

    @RequestMapping("/about")
    public String about(){
        return "about page";
    }
}
