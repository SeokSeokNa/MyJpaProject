package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.api_dto.BoardApiDto;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository2 extends JpaRepository<Board, Long> {

    Optional<Board> findOptionalById(Long id);



    @EntityGraph(attributePaths = {"user"})
    @Query(value = "select b from Board b " +
            "where b.title like %:searchText% or b.contents like %:searchText% order by b.writeDate desc , b.id desc "
            , countQuery = "select count (b) from Board b")
    Page<Board> findBoardAll(Pageable pageable , @Param("searchText") String searchText);

    @EntityGraph(attributePaths = {"user"})
    @Query(value = "select b from Board b join fetch b.user u order by b.id desc")
    List<Board> callBoardApi();

    @EntityGraph(attributePaths = {"user"})
    Slice<Board> findByTitleContainsOrContentsContains(@Param("title")String title,@Param("contents")String contents,Pageable pageable);




}
