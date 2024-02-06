package com.study.board.controller;

import javax.swing.border.Border;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller //스프링에게 이 파일이 컨트롤러라는 것을 알려주는 어노테이션
public class Boardcontroller {

    @Autowired
    private BoardService boardService;
    
    // @GetMapping("/") //로컬 호스트 8090 주소로 들어갔을때 자동으로 역슬래쉬를 붙여주는 어노테이션
    // @ResponseBody //역슬래쉬로 들어왔을 때 글자를 띄워주는 어노테이션
    // public String main() {
    //     return "Hello World";
    // }

    @GetMapping("/board/write") //localhost:8090/board/write로 접속하면 boardWrite 페이지를 보여준다.
    public String boardWriteForm() {
        return "boardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model) {
        
        boardService.write(board);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList());
        return "boardList";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1 -> id가 1번인 게시물을 불러온다
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/delete") 
    public String boardDelete(Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}") 
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board) {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp);
        return "redirect:/board/list";
    }

}
