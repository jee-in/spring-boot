package com.example.demo.lombok;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConstructorTest {

    @Test
    @DisplayName("클래스는 디폴트로 기본 생성자를 가진다.")
    public void noArgConstructorTest() {

        class Member {
            private long id;
            private String name;
            private int age;
            private String email;

            @Override
            public String toString() {
                return "Member { id: " + id + ", name: " + name + ", age: " + age + ", email: " + email + " }";
            }
        }

        Member member = new Member();
        System.out.println(member); // Member { id: 0, name: null, age: 0, email: null }
    }

    @Test
    @DisplayName("생성자를 정의할 경우 디폴트 기본 생성자는 사라진다.")
    public void someArgsConstructorTest() {

        class Member {
            private long id;
            private String name;
            private int age;
            private String email;

            public Member(long id, String name) {
                this.id = id;
                this.name = name;
            }

            @Override
            public String toString() {
                return "Member { id: " + id + ", name: " + name + ", age: " + age + ", email: " + email + " }";
            }
        }

        Member member = new Member(1L, "John");
        System.out.println(member);
    }

    @Test
    @DisplayName("기본 생성자와 전체 생성자가 모두 필요하다면 @NoArgsConstructor와 @AllArgsConstructor가 모두 필요하다.")
    public void EntireConstructorTest() {

        @NoArgsConstructor
        @AllArgsConstructor
        class Member {
            private long id;
            private String name;
            private int age;
            private String email;

            @Override
            public String toString() {
                return "Member { id: " + id + ", name: " + name + ", age: " + age + ", email: " + email + " }";
            }
        }

        Member member = new Member();
        System.out.println(member);

        Member member2 = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member2);
    }

}
