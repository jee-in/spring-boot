package com.example.demo.jackson.serialization.nested.data;

import com.example.demo.jackson.serialization.basic.data.success.MemberGetter;
import lombok.Getter;

@Getter
public class NestedMemberGetter extends MemberGetter {
    private Order order = new Order();

    @Getter
    public class Order {
        private long id = 2L;
        private String name = "콜라";
    }
}
