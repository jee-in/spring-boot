package com.example.demo.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReferencingMemberGetter {
    private long id;
    private String name;
    private int age;
    private String email;

    private PrivacyGetter privacy;
}
