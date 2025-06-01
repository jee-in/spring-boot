package com.example.demo.jackson.serialization.collection.data;

import com.example.demo.jackson.serialization.basic.data.success.MemberGetter;
import lombok.Getter;

@Getter
public class ReferencingMemberGetter extends MemberGetter {
    private MemberDetailGetter memberDetail = new MemberDetailGetter();
}
