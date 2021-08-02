package com.firstjpa.minijpa.controller;

import com.firstjpa.minijpa.MiniJpaApplication;
import com.firstjpa.minijpa.controller.Form.BoardForm;
import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.Photo;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.service.BoardService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/new")
    public String boardForm(Model model) {
        model.addAttribute("boardForm" , new BoardForm());
        return "board/boardForm";
    }

    @GetMapping("/board/new2")
    public String boardForm2(Model model) {
        model.addAttribute("boardForm" , new BoardForm());
        return "board/boardForm2";
    }

    //게시글 등록
    @PostMapping("/board/new")
    public String boardPost(
            @Valid BoardForm boardForm ,
            BindingResult result ,
            HttpSession session ,
            @RequestParam(value="uploadfile", required=false) List<MultipartFile> uploadfile,
            HttpServletRequest request) {

        //Valid 체크
        if (result.hasErrors()) {
            return "board/boardForm";
        }
        User user = (User)session.getAttribute("userinfo");
//        Board board = Board.createBoard(user, boardForm.getTitle(), boardForm.getContents() ,uploadfile);
//        boardService.save(board);
        return "redirect:/";
    }

    //게시글 등록
    @PostMapping("/board/new2")
    public String boardPost2(
            @Valid BoardForm boardForm ,
            BindingResult result ,
            HttpSession session ,
            @RequestParam(value = "files" , required = false) MultipartFile[] files,
            @RequestParam(value = "test") String appendVal,
            HttpServletRequest request) {


        //Valid 체크
        if (result.hasErrors()) {
            return "board/boardForm";
        }
        System.out.println(appendVal);
//        System.out.println(files[0].getOriginalFilename());

//        User user = (User)session.getAttribute("userinfo");
//        Board board = Board.createBoard2(user, boardForm.getTitle(), boardForm.getContents() ,files);
//        boardService.save(board);
        return "redirect:/";
    }


    @GetMapping("/board/list")
    public String boardList(Model model) {
        List<Board> boardList = boardService.boardList();
        model.addAttribute("boardList",boardList);
        return "/board/boardList";
    }

    @GetMapping("/board/list2")
    public String boardList2(Model model) {
        List<Board> boardList = boardService.boardList();
        model.addAttribute("boardList",boardList);
        return "/board/boardList2";
    }
}
