package com.poem.poem.controller;

import com.poem.poem.domain.WritingType;
import com.poem.poem.service.WritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // 화면(HTML) 반환 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/writings")
// /writings -> controller가 받아서 service한테 '글 목록 줘'하고 결과 화면(타임리프)에 넘기기
// 이 컨트롤러의 모든 URL /writings로 시작
public class WritingController {

    private final WritingService writingService;

    @GetMapping // GET 요청 처리, 페이지 보여줄 때
    public String list(Model model) { // Model: 데이터를 넘기는 가방
        model.addAttribute("writings", writingService.findAll());
        return "writing/list"; // templates/writing/list.html 파일 출력
    }

    @GetMapping("/new")
    public String newForm() {
        return "writing/form";
    }

    @PostMapping
    public String create(@RequestParam String content,
                         @RequestParam WritingType type,
                         @RequestParam(required = false) String tags) {
        // @RequestParam: HTTP 요청의 폼 데이터/쿼리 파라미터에서 가져오기
        writingService.save(content, type, tags);
        return "redirect:/writings"; // 글 생성/삭제 후 목록 페이지로 돌아가기
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // @PathVariable: URL 경로에서 가져오기 (/writings/{id} → id값)
        model.addAttribute("writing", writingService.findById(id));
        return "writing/detail";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        writingService.delete(id);
        return "redirect:/writings";
    }
}