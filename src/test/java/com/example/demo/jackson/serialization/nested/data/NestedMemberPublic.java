package com.example.demo.jackson.serialization.nested.data;

import com.example.demo.jackson.serialization.basic.data.success.MemberGetter;
import lombok.Getter;

@Getter
public class NestedMemberPublic extends MemberGetter {
    private Order order = new Order();

    public class Order {
        public long id = 2L;
        public String name = "콜라";
    }
}
