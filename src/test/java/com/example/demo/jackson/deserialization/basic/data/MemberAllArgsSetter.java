package com.example.demo.jackson.deserialization.basic.data;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Setter
public class MemberAllArgsSetter {
    private long id;
    private String name;
    private int age;
    private String email;
}
