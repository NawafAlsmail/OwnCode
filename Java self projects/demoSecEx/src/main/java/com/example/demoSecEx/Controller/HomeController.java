package com.example.demoSecEx.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String greeting(HttpServletRequest request) {
        return "Hello world " + request.getSession().getId();
    }
}
