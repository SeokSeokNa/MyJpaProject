package com.firstjpa.minijpa.service;

import com.firstjpa.minijpa.domain.PostWrite;
import com.firstjpa.minijpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long savePost(PostWrite post) {
        postRepository.save(post);
        return post.getId();
    }
}
