package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository2 extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query(value = "select b from Board b  where b.title like %:title% or b.contents like %:contents% order by b.writeDate desc , b.id desc "
            , countQuery = "select count (b) from Board b")
    Page<Board> findBoardAll(Pageable pageable , @Param("title") String title , @Param("contents")String contents);

}
