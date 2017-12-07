package com.example.junior.Controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/h")
@EnableAutoConfiguration
public class HelloController {

    @RequestMapping("/hello")
    public String say()
    {
        System.out.println("进入了HelloController");
        return "hello";
    }
}
