package com.example.demoWeb.Controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Operation(summary = "sign in")
    @GetMapping("/login")
    public String login(){
        return "Logging in...";
    }
}
