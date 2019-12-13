package com.zhj.demo1.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloWorld {
    @RequestMapping("/helloWorld")
    public String HelloWorld(){
        return "helloWorld";
    }
}
