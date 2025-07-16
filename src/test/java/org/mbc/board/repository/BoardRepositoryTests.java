package org.mbc.board.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mbc.board.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository; // JPA 인터페이스

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("제목..." + i)
                    .content("내용..." + i)
                    .writer("user..." + (i % 10))
                    .build();

            log.info(board); // 로그 확인용
            Board result = boardRepository.save(board); // 저장 실행
            log.info("게시물 번호 출력"+ result.getBno() + "게시물의 제목"+
                    result.getTitle());
        });
    }
    
    
    @Test
    public void testSelect(){
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        // null값이 나올 경우를 대비한 객체
        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        // 가져온 값이 있으면 board 타입 객체에 할당
        board.change("수정 테스트 제목", "수정 테스트 내용");
        boardRepository.save(board); // .save라는 메서드는 없으면 insert / 있으면 update

        // Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Hibernate:
        //    update
        //        board
        //    set
        //        content=?,
        //        moddate=?,
        //        title=?,
        //        writer=?
        //    where
        //        bno=?
    }

    @Test
    public void testDelete(){
        Long bno = 1L;
        boardRepository.deleteById(bno);

    }

    @Test
    public void testPaging(){
        Pageable pageable = PageRequest.of(19,5, Sort.by("bno").descending());
// Hibernate:
//    select
//        b1_0.bno,
//        b1_0.content,
//        b1_0.moddate,
//        b1_0.regdate,
//        b1_0.title,
//        b1_0.writer
//    from
//        board b1_0
//    order by
//        b1_0.bno desc
//    limit
//        ?, ?
//Hibernate:
//    select
//        count(b1_0.bno)
//    from
//        board b1_0
        Page<Board> result = boardRepository.findAll(pageable);
        // Page 클래스: 이전/다음 페이지 존재 여부, 데이터 개수 등을 계산함
        log.info("전체 게시물 수: " + result.getTotalElements());
        log.info("총 페이지 수: " + result.getTotalPages());
        log.info("현재 페이지 번호: " + result.getNumber());
        log.info("페이지의 크기: " + result.getSize());
        log.info("다음 페이지 여부: " + result.hasNext());
        log.info("시작 페이지 여부: " + result.isFirst());

        // 콘솔에 결과를 출력해보자
        List<Board> boardList = result.getContent(); // 페이징 처리된 내용을 가져와
        boardList.forEach(board -> log.info(board));
        // forEach는 인덱스를 사용하지 않고 앞에서부터 객체를 리턴함
    }
}
