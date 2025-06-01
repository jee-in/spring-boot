package com.example.demo.jackson.deserialization.spring.data;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberAllArgs {
    private long id;
    private String name;
    private int age;
    private String email;
}
