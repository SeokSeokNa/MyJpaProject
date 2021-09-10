package com.firstjpa.minijpa.api;

import com.firstjpa.minijpa.api_dto.BoardApiDto;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.BoardRepository2;
import com.firstjpa.minijpa.repository.UserRepository2;
import com.firstjpa.minijpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardRepository2 boardRepository;
    private final BoardService boardService;
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
            HttpServletRequest req,
            @RequestAttribute(name = "userId")String userId ,
            @RequestParam(value = "title") String title ,
            @RequestParam(value = "content") String content ,
            @RequestParam(value = "files" , required = false) MultipartFile[] files) {


        System.out.println("title = " + title);
        System.out.println("content = " + content);
        Optional<User> findUser = userRepository.findByUserId(userId);
        User user = findUser.get();

        Board board = Board.createBoard2(user, title, content, files);
        System.out.println(board.getTitle());
        System.out.println(board.getContents());
        req.setAttribute("user_name" , user.getName());
        return boardRepository.save(board).getId();
    }

    @GetMapping("/api/v1/boardDetail/{id}")
    public BoardApiDto boardDetail(@PathVariable("id") Long id) {
        Optional<Board> board = boardRepository.findOptionalById(id);
        BoardApiDto boardApiDto;
        if (board.isPresent()) {
            boardApiDto = new BoardApiDto(board.get());
        } else {
            boardApiDto = null;
        }

        return boardApiDto;
    }

    @PutMapping("/api/v1/boardModify/{id}")
    public Long boardModify(@PathVariable("id") Long id,
                            @RequestParam(value = "title") String title ,
                            @RequestParam(value = "content") String content ,
                            @RequestParam(value = "files" , required = false) MultipartFile[] files)
    {
        Optional<Board> board = boardRepository.findOptionalById(id);
        Board findBoard = board.get();
        findBoard.photoUpload(findBoard,files);
        boardService.update(findBoard , title , content);
        return findBoard.getId();
    }

    @DeleteMapping("/api/v1/boardDelete/{id}")
    public Long boardDelete(@PathVariable("id") Long id) {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isPresent()) {
            Board findBoard = board.get();
            Board.deletePhoto(findBoard);
            boardRepository.delete(findBoard);
            return findBoard.getId();
        }
        return null;
    }
}
