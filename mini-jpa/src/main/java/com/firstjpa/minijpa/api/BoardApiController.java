package com.firstjpa.minijpa.api;

import com.firstjpa.minijpa.api_dto.BoardApiDto;
import com.firstjpa.minijpa.controller.Form.BoardForm;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.BoardRepository2;
import com.firstjpa.minijpa.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardRepository2 boardRepository;
    private final UserRepository2 userRepository;

    @GetMapping("/api/v1/board/list")
    public List<BoardApiDto> boardV1_page() {
        List<Board> boards = boardRepository.callBoardApi();
        List<BoardApiDto> collect = boards.stream()
                .map(b -> new BoardApiDto(b))
                .collect(Collectors.toList());
        return collect;
    }

    @PostMapping("/api/v1/board/new")
    public Long postBoard(
            @RequestAttribute(name = "userId")String userId ,
            @RequestParam(value = "title") String title ,
            @RequestParam(value = "content") String content ,
            @RequestParam(value = "files" , required = false) MultipartFile[] files) {


        System.out.println("title = " + title);
        System.out.println("content = " + content);
        System.out.println("files = " + files[0].getOriginalFilename());
        Optional<User> findUser = userRepository.findByUserId(userId);
        User user = findUser.get();

        Board board = Board.createBoard2(user, title, content, files);
        System.out.println(board.getTitle());
        System.out.println(board.getContents());

        return boardRepository.save(board).getId();
    }
}
