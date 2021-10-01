package com.epam.esm;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.epam.esm")
public class Controller {

    @GetMapping("/")
    public String home() {
        return "123";
    }
}
