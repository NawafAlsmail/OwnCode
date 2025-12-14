package com.example.demoWeb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
