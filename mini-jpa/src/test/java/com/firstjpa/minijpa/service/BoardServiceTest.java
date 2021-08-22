package com.firstjpa.minijpa.service;

import com.firstjpa.minijpa.controller.Form.BoardForm;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.BoardRepository;
import com.firstjpa.minijpa.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        user.setUserId("test1");
        userRepository.save(user);

        BoardForm boardForm = new BoardForm();
        boardForm.setTitle("제목");
        boardForm.setContents("내용내용");

//        Board post = Board.createBoard(user, boardForm.getTitle(), boardForm.getContents() , );
//        boardRepository.save(post);
//
//        Assertions.assertEquals("제목" , post.getTitle());
    }

    @Test
    public void upHit() throws Exception {
        //given
        Board board = boardRepository.findById(3L);
        //when
        board.upHit();

        //then
        Assertions.assertThat(board.getHit()).isEqualTo(1);
    }
}