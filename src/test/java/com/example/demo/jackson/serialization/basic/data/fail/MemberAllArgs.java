package com.example.demo.jackson.serialization.basic.data.fail;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MemberAllArgs {
    private long id = 1L;
    private String name = "홍길동";
    private int age = 20;
    private String email = "gildong@korea.com";
}
