package com.example.demo.jackson.deserialization.basic.data;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class MemberSetter {
    private long id;
    private String name;
    private int age;
    private String email;
}

