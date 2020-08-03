package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
* @MappedSuperclass : JPA엔티티들이 BaseTimeEntity를 상속할경우 필드들(createdTime,modifiedTime)도 컬럼으로 인식하도록 함.
* @EntityListeners(AuditingEntityListener.class) : BaseTimeEntity 클래스에 Auditing 기능 포함.
* */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    //@CreatedDate : Entity가 생성되어 저장될때 시간이 자동 저장됨.
    @CreatedDate
    private LocalDateTime createdDate;

    //@LastModifiedDate : 조회한 Entity의 값을 변경할 때 시간이 자동 저장됨.
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
