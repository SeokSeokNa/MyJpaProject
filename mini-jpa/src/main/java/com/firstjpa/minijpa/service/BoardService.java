package com.firstjpa.minijpa.service;

import com.firstjpa.minijpa.MiniJpaApplication;
import com.firstjpa.minijpa.controller.Form.BoardForm;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.Photo;
import com.firstjpa.minijpa.repository.BoardRepository;
import com.firstjpa.minijpa.repository.BoardRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardRepository2 boardRepository2;

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

    @Transactional
    public void update(Board board , String title , String contents) {
        boardRepository.updateBoard(board , title , contents);
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board board = findById(id);
        List<Photo> photos = board.getPhotos();
        File file = null;
        for (Photo photo : photos) {
            file = new File(MiniJpaApplication.UPLOAD_PATH , photo.getNewFileName());
            if( file.exists()) file.delete();
        }
        boardRepository.deleteBoard(board);
    }

    @Transactional
    public void deletePhoto(Long id , int index) {
        Board board = findById(id);
        List<Photo> photos = board.getPhotos();
        File file = new File(MiniJpaApplication.UPLOAD_PATH,board.getPhotos().get(index).getNewFileName());
        if( file.exists()) file.delete();
        boardRepository.deletePhoto(photos , index);
    }

    public Page<Board> boardAll(Pageable pageable) {
        return boardRepository2.findAll(pageable);
    }
}
