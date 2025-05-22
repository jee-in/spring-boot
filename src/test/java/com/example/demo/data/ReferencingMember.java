package com.example.demo.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReferencingMember {
    private long id;
    private String name;
    private int age;
    private String email;

    private Privacy privacy;
}
