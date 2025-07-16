package org.mbc.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 공통적인 최상위 클래스로 만듦
@EntityListeners(value = AuditingEntityListener.class) // 감시용 클래스 명시
@Getter // 날짜용 처리만 (sysdate) -> 데베의 날짜를 가져오기만 하겠다
abstract class BaseEntity { // 추상적 클래스. 자체적으로 실행 불가
    // 모든 테이블에 공통적으로 사용되는 필드를 만든다
    @CreatedBy
    @Column(name="regdate", updatable = false)
    // updatable = false < 수정이 안 된다는 뜻
    private LocalDateTime regDate;
    @Column(name="moddate") // db 필드명을 지정
    @LastModifiedDate
    private LocalDateTime modDate;
}
