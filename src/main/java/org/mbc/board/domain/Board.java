package org.mbc.board.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity // db 테이블 관련 객체
@Getter
@Builder // 빌더 패턴. 세터 대신 사용
@AllArgsConstructor // 필드 생성자
@NoArgsConstructor // 기본 생성자
@ToString
public class Board extends BaseEntity {
    @Id // (notnull, unique, indexing)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동번호 생성용
    private long bno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    // Hibernate:
    //    alter table if exists board
    //       add column moddate datetime(6)
    //Hibernate:
    //    alter table if exists board
    //       add column regdate datetime(6)
    //Hibernate:
    //    alter table if exists board
    //       modify column content varchar(2000) not null
    //Hibernate:
    //    alter table if exists board
    //       modify column title varchar(500) not null
    //Hibernate:
    //    alter table if exists board
    //       modify column writer varchar(50) not null

    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }

}
