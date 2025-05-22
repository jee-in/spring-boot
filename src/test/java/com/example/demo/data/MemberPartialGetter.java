package com.example.demo.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberPartialGetter {
    private long id;
    private String name;
    private int age;
    private String email;

    public String getEmail() {
        return email;
    }
}

