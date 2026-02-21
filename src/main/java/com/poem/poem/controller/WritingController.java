package com.poem.poem.controller;

import com.poem.poem.domain.Writing;
import com.poem.poem.domain.WritingType;
import com.poem.poem.service.PoemService;
import com.poem.poem.service.WritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // 화면(HTML) 반환 컨트롤러
@RequiredArgsConstructor
@RequestMapping("/writings")
// /writings -> controller가 받아서 service한테 '글 목록 줘'하고 결과 화면(타임리프)에 넘기기
// 이 컨트롤러의 모든 URL /writings로 시작
public class WritingController {

    private final WritingService writingService;
    private final PoemService poemService;

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
                         @RequestParam(required = false) List<String> tags) {
        String joinedTags = (tags != null) ? String.join(",", tags) : null;
        writingService.save(content, type, joinedTags);
        return "redirect:/writings";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Writing writing = writingService.findById(id);
        model.addAttribute("writing", writing);
        model.addAttribute("recommendedPoems", poemService.findByTagsRanked(writing.getTags()));
        return "writing/detail";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        writingService.delete(id);
        return "redirect:/writings";
    }
}