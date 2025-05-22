package com.example.demo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class Member {
    private long id;
    private String name;
    private int age;
    private String email;
}
