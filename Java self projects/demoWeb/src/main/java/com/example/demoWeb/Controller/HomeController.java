package com.example.demoWeb.Controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class HomeController {

    @Operation(summary = "greeting")
    @GetMapping("/")
//    @ResponseBody                          /cz we have putten @RestController
    public String greeting(){
        return "Hello World!";
    }
    @Operation(summary = "info")
    @GetMapping("/about")
    public String about(){
        return "about page";
    }
}
