package com.example.demo.jackson.serialization.basic.data.fail;

public class MemberPartialGetter {
    private long id = 1L;
    private String name = "홍길동";
    private int age = 20;
    private String email = "gildong@korea.com";

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
