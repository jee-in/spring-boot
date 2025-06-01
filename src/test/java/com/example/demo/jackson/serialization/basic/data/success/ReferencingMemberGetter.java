package com.example.demo.jackson.serialization.basic.data.success;

import lombok.Getter;

@Getter
public class ReferencingMemberGetter extends MemberGetter {
    private MemberDetailGetter memberDetail = new MemberDetailGetter();
}
