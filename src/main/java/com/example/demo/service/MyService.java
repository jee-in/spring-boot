package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class MyService {
    public void hello() {
        System.out.println("MyService bean is active!");
    }
}
