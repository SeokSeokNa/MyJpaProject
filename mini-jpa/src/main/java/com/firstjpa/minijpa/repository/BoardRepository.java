package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    //게시글 등록
    public void save(Board board) {
        em.persist(board);
    }

    //게시글 아이디로 조회(디테일페이지)
    public Board findById(Long id) {
        return em.find(Board.class , id);
    }

    //게시글  전체조회
    public List<Board> findAll() {
       return em.createQuery(
                "select b from Board b" +
                        " join fetch b.user u",Board.class)
                .getResultList();
    }

    //게시글 수정
    public void updateBoard(Board board , String title, String contents) {
        board.setTitle(title);
        board.setContents(contents);
    }

    //게시글 삭제
    public void deleteBoard(Board board) {
        em.remove(board);
    }

    //게시글 수정시 하나하나 사진 지우기
    public void deletePhoto(List<Photo> photos , int index) {
        photos.remove(index);
    }

}
