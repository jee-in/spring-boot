package com.example.demo.data;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NestedMember {
    private long id;
    private String name;
    private int age;
    private String email;
    private List<SubscribingChannel> subscribingChannels;

    @AllArgsConstructor
    @Getter
    public static class SubscribingChannel {
        private String title;
        private String creator;
    }
}
