package com.poem.poem.controller;

import com.poem.poem.domain.Poem;
import com.poem.poem.dto.PoemResponse;
import com.poem.poem.service.PoemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/poems")
public class PoemController {

    private final PoemService poemService;

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Poem poem = poemService.findById(id);
        model.addAttribute("poem", new PoemResponse(poem));
        // 엔티티를 DTO로 변환해서 넘김
        return "poem/detail";
    }
}