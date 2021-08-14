package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository2 extends JpaRepository<Board, Long> {

    @Query(value = "select b from Board b", countQuery = "select count (b) from Board b")
    Page<Board> findAll(Pageable pageable);

}
