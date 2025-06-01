package com.example.demo.jackson.deserialization.spring.data;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MemberNoArgsGetter {
    private long id;
    private String name;
    private int age;
    private String email;
}
