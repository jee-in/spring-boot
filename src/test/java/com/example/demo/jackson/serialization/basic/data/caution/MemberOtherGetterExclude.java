package com.example.demo.jackson.serialization.basic.data.caution;

import com.example.demo.jackson.serialization.basic.data.success.MemberGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MemberOtherGetterExclude extends MemberGetter {

    @JsonIgnore
    public String getMessage() {
        return "Hello World";
    }
}
