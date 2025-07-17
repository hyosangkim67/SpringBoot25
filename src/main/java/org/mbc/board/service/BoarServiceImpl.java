package org.mbc.board.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mbc.board.domain.Board;
import org.mbc.board.dto.BoardDTO;
import org.mbc.board.repository.BoardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoarServiceImpl implements BoardService {

    private final ModelMapper modelMapper; // entity <-> DTO 변환
    private final BoardRepository boardRepository; // jpa용 클래스

    @Override
    public Long register(BoardDTO boardDTO) { // 조원이 실행코드를 만든다
        // form에서 넘어온 DTO가 데베에 기록되어야 함

        Board board = modelMapper.map(boardDTO, Board.class);
        // entity <-> DTO 변환
        Long bno = boardRepository.save(board).getBno();
        // insert into board -> bno를 받는다
        return bno; // 프론트에 게시물 저장 후 번호가 전달됨
    }

    @Override
    public BoardDTO readone(Long bno){
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());
        Board board = result.orElseThrow();
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);

    }

    @Override
    public void remove(Long bno){
        boardRepository.deleteById(bno);
        // delete from board where bno = bno ;
    }
}
