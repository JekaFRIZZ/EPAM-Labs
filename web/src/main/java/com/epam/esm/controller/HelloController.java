package com.epam.esm.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String home() {
        return "Hello!";
    }
}
