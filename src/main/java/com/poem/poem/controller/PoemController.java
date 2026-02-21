package com.poem.poem.controller;

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
        model.addAttribute("poem", poemService.findById(id));
        return "poem/detail";
    }
}