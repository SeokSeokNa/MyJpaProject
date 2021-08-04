package com.firstjpa.minijpa.controller;

import com.firstjpa.minijpa.controller.Form.BoardForm;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 폼 이동
    @GetMapping("/board/new")
    public String boardForm(Model model) {
        model.addAttribute("boardForm" , new BoardForm());
        return "board/boardForm";
    }



    //게시글 등록
    @ResponseBody // ajax에 응답을 주기위해 사용한 어노테이션
    @PostMapping("/board/new")
    public HashMap<String ,String> boardPost(
            @Valid BoardForm boardForm ,
            BindingResult result ,
            HttpSession session ,
            @RequestParam(value = "files" , required = false) MultipartFile[] files,
//            @RequestParam(value = "test") String test,
            HttpServletRequest request) {

        HashMap<String, String> returnMap = new HashMap<>();


        //Valid 체크
        if (result.hasErrors()) { //valid통과 못한부분만 맵에 담아서 넘기기
            returnMap.put("success","false");
            for (Object object : result.getAllErrors()){
                FieldError fieldError = (FieldError) object;
                returnMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return returnMap;

        }
        User user = (User)session.getAttribute("userinfo");
        Board board = Board.createBoard2(user, boardForm.getTitle(), boardForm.getContents() ,files);
        boardService.save(board);
        returnMap.put("success","true");

        return returnMap;
    }

    @GetMapping("/board/list")
    public String boardList2(Model model) {
        List<Board> boardList = boardService.boardList();
        model.addAttribute("boardList",boardList);
        return "board/boardList";
    }
}
