package com.firstjpa.minijpa.api;

import com.firstjpa.minijpa.api_dto.BoardApiDto;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.dto.BoardDto;
import com.firstjpa.minijpa.repository.BoardRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardRepository2 boardRepository;

    @GetMapping("/api/v1/board/list")
    public List<BoardApiDto> boardV1_page() {
        List<Board> boards = boardRepository.callBoardApi();
        List<BoardApiDto> collect = boards.stream()
                .map(b -> new BoardApiDto(b))
                .collect(Collectors.toList());
      return collect;
    }


}
