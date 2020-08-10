package com.jojoldu.book.springboot.domain.user;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//BaseTimeEntity 클래스 상속만으로 entity의 생성/수정시간 필드가 추가되고 기능 수행때마다 초기화된다. JPA auditing기능.
@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    //@Enumerated(EnumType.STRING) : JPA로 DB에 값 저장할때 Enum값을 어떤 형식으로 저장할것인가. 기본은 int형 숫자.
    //숫자로 저장되면 그게 무슨의미인지 알 수 없으므로 문자형으로 저장.
    //Role 클래스 : 사용자 권한관리 Enum 클래스.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey(); //key : ROLE_USER 등의 권한을 알수있는 변수값.
    }
}
