package com.firstjpa.minijpa.service;

import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id);
    }

    public List<Board> boardList() {
        return boardRepository.findAll();
    }
}
