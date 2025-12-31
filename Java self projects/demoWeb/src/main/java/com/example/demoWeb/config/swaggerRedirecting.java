package com.example.demoWeb.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// an old one
@Controller
public class swaggerRedirecting {
    @GetMapping("/swagger-ui.html")
    public String swaggerUI() {
        return "redirect:/swagger";
    }

    @GetMapping("/nawaf")
    public String swaggerUI2() {
        return "redirect:/swagger";
    }
}
