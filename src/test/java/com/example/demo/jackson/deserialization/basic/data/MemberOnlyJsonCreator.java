package com.example.demo.jackson.deserialization.basic.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.ToString;

@ToString
public class MemberOnlyJsonCreator {
    private long id;
    private String name;
    private int age;
    private String email;

    @JsonCreator
    public MemberOnlyJsonCreator(long id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
