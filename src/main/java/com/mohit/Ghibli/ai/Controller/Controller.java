package com.mohit.Ghibli.ai.Controller;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostConstruct
    public void init() {
        System.out.println("✅ Controller is loaded");
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

}
