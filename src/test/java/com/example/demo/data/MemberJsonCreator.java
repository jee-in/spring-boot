package com.example.demo.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MemberJsonCreator {
    private long id;
    private String name;
    private int age;
    private String email;

    @JsonCreator
    public MemberJsonCreator(
        @JsonProperty("id") long id,
        @JsonProperty("name") String name,
        @JsonProperty("age") int age,
        @JsonProperty("email") String email
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
