package com.example.demo.lombok;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ToStringTest {

    @Test
    @DisplayName("@ToString을 이용하면 toString() 메서드가 오버라이드된다.")
    public void testToString() {

        @ToString
        @AllArgsConstructor
        class Member {
            private long id;
            private String name;
            private int age;
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동, age=20, email=gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: includeFieldNames")
    public void testToStringOptionIncludeFieldNames() {

        @ToString(includeFieldNames = false)
        @AllArgsConstructor
        class Member {
            private long id;
            private String name;
            private int age;
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(1, 홍길동, 20, gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: exclude (deprecated 예정)")
    public void testToStringOptionExclude() {

        @ToString(exclude = {"id", "email"})
        @AllArgsConstructor
        class Member {
            private long id;
            private String name;
            private int age;
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        String memberString = member.toString();
        assertThat(memberString).doesNotContain("id").doesNotContain("email");
        System.out.println(member); // Member(name=홍길동, age=20)
    }

    @Test
    @DisplayName("@ToString 옵션: 없는 필드를 exclude 하면 warning이 발생한다. (deprecated 예정)")
    public void testToStringOptionExcludeWarning() {

        @ToString(exclude = "address") // warning: This field does not exist, or would have been excluded anyway.
        @AllArgsConstructor
        class Member {
            private long id;
            private String name;
            private int age;
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동, age=20, email=gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: of (deprecated 예정)")
    public void testToStringOptionOf() {

        @ToString(of = {"id", "name"})
        @AllArgsConstructor
        class Member {
            private long id;
            private String name;
            private int age;
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동)
    }

    @Test
    @DisplayName("@ToString 옵션: exclude와 of를 동시에 사용하면 경고가 발생한다. 둘 중 하나만 사용해야 한다. (deprecated 예정)")
    public void testToStringOptionOfAndExclude() {

        @ToString(of = {"id", "name"}, exclude = "email") // Parameters 'exclude' and 'of' are mutually exclusive; the 'exclude' parameter will be ignored
        @AllArgsConstructor
        class Member {
            private long id;
            private String name;
            private int age;
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동)
    }

    @Test
    @DisplayName("@ToString 옵션: 필드 또는 getter에 @ToString.Exclude 를 쓰면 해당 필드를 문자열화에서 제외할 수 있다.")
    public void testToStringExclude() {

        @ToString
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;

            @ToString.Exclude
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동, age=20)
    }

    @Test
    @DisplayName("@ToString 옵션: 필드 또는 getter에 @ToString.Exclude와 @ToString.Include를 동시에 쓰면 에러가 발생한다.")
    public void testToStringExcludeAndInclude() {

        @ToString
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;

            @ToString.Include
            //@ToString.Exclude // error: @ToString.Exclude and @ToString.Include are mutually exclusive; the @Include annotation will be ignored
            private String email;
        }
    }

    @Test
    @DisplayName("@ToString 옵션: @ToString.Include를 명시적으로 하지 않아도 모든 필드는 문자열화된다.")
    public void testToStringExplicitlyInclude() {

        @ToString
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;

            @ToString.Include
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동, age=20, email=gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: @ToString.Include를 쓸 때 필드 이름을 지정할 수 있다.")
    public void testToStringIncludeCustom() {

        @ToString
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;

            @ToString.Include(name="이메일")
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동, age=20, 이메일=gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: @ToString.Include를 쓸 때 출력 순서를 지정할 수 있다.")
    public void testToStringIncludeCustomRank() {

        @ToString
        @AllArgsConstructor
        class Member {

            @ToString.Include(rank = 1)
            private long id;

            @ToString.Include(rank = 2)
            private String name;

            @ToString.Include(rank = 100)
            private int age;

            @ToString.Include(rank = 10)
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(age=20, email=gildong@korea.com, name=홍길동, id=1)
    }

    @Test
    @DisplayName("@ToString 옵션: onlyExplicitlyIncluded를 true로 설정하면 명시적으로 포함시킨 필드만 문자열에 포함된다.")
    public void testToStringOnlyExplicitlyInclude() {

        @ToString(onlyExplicitlyIncluded = true)
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;

            @ToString.Include
            private String email;
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(email=gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: ToString.Include를 getter에 설정할 수도 있다.")
    public void testToStringIncludeOptionToGetter() {

        @ToString(onlyExplicitlyIncluded = true)
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;
            private String email;

            @ToString.Include
            public String getEmail() {
                return email;
            }
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(getEmail=gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: ToString.Include를 필드와 getter 모두에 설정하면 둘 다 출력된다.")
    public void testToStringIncludeOptionToGetterAndField() {

        @ToString(onlyExplicitlyIncluded = true)
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;

            @ToString.Include
            private String email;

            @ToString.Include
            public String getEmail() {
                return email;
            }
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(email=gildong@korea.com, getEmail=gildong@korea.com)
    }

    @Test
    @DisplayName("@ToString 옵션: doNotUseGetters를 true로 설정하면 getter는 호출되지 않는다.")
    public void testToStringDoNotUseGetters() {

        @ToString(doNotUseGetters = true)
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;
            private String email;

            public String getEmail() {
                System.out.println("getEmail() is called"); // 호출되지 않음
                return email;
            }
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // Member(id=1, name=홍길동, age=20, email=gildong@korea.com)

    }

    @Test
    @DisplayName("@ToString 옵션: getter에 @ToString.Include를 설정하고 doNotUseGetters를 true로 설정하면 @ToString.include가 우선된다.")
    public void testToStringGetterIncludeAndDonotUseGetters() {

        @ToString(doNotUseGetters = true)
        @AllArgsConstructor
        class Member {

            private long id;
            private String name;
            private int age;
            private String email;

            @ToString.Include
            public String getEmail() {
                System.out.println("getEmail() is called");
                return email;
            }
        }

        Member member = new Member(1L, "홍길동", 20, "gildong@korea.com");
        System.out.println(member); // getEmail() is called, Member(id=1, name=홍길동, age=20, email=gildong@korea.com, getEmail=gildong@korea.com)
        System.out.println(member); // getEmail() is called, Member(id=1, name=홍길동, age=20, email=gildong@korea.com, getEmail=gildong@korea.com)
    }
}
