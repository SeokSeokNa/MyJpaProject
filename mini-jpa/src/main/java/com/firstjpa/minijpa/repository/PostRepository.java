package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.domain.PostWrite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final EntityManager em;

    //게시글 등록
    public void save(PostWrite post) {
        em.persist(post);
    }

}
