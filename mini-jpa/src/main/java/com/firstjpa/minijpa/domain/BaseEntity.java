package com.firstjpa.minijpa.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) //BaseEntity는 이벤트를 기반으로 동작할것이다 라고 명시하는 어노테이션
@MappedSuperclass
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy //등록될때마다 메인(DataJpaApplication)에 auditorProvider()를 호출해서 값을 자동으로 채워놓는다.
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy //수정될때마다 메인(DataJpaApplication)에 auditorProvider()를 호출해서 값을 자동으로 채워놓는다.
    private String lastModifiedBy;
}
