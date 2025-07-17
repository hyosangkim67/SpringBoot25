package org.mbc.board.service;

import org.mbc.board.dto.BoardDTO;

public interface BoardService {
    // 조장용 코드 > 시그니처만 필요함 * impl 구현 클래스

    Long register(BoardDTO boardDTO); // return bno

    BoardDTO readone(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);
}
