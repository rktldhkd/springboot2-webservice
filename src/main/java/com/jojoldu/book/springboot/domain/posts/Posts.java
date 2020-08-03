package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
* @NoArgsConstructor : 기본 생성자 자동 추가
* @Entity : 테이블과 매핑될 클래스 선언. 기본값으로 카멜케이스이름(자바클래스명)을 스네이크표기명(DB테이블명)으로 테이블과 매칭함.
* */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;
    //@Column선언 안해도 클래스의 필드는 모두 column이 된다. : 기본값 사용시 생략 가능.
    //기본값외에 다른값으로 변경,설정해야할 경우만 명시해서 설정한다.

    /*
    * @Builder
    * ㄴ해당클래스의 빌더 패턴 클래스 생성.
    * ㄴ생성자 상단에 선언 시, 생성자에 포함된 필드만 빌더에 포함.
    * */
    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
    /*
    Setter가 없으므로 값을채워 DB에 insert 해야하는 상황일 때,  생성자를 통해 최종값을 채운 후 DB에
    삽입해야하며 값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제로 한다.
    @Builder를 통해 제공되는 빌더클래스를 생성하여 생성자 대신 사용해도 된다.
     */

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
