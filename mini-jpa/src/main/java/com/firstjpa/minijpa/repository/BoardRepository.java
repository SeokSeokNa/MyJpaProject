package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.domain.Board;
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

}
