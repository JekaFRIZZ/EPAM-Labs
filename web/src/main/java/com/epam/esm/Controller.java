package com.epam.esm;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.epam.esm")
public class Controller {

    @GetMapping("123")
    public String add() {
        return "123";
    }
}
