package com.poem.poem.controller;

import com.poem.poem.domain.Writing;
import com.poem.poem.domain.WritingType;
import com.poem.poem.dto.PoemResponse;
import com.poem.poem.dto.WritingResponse;
import com.poem.poem.repository.WritingRepository;
import com.poem.poem.service.PoemService;
import com.poem.poem.service.WritingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WritingController {

    private final WritingRepository writingRepository;
    private final WritingService writingService;
    private final PoemService poemService;

    @GetMapping("/")
    public String home(Model model) {
        List<Writing> quotes = writingRepository.findByType(WritingType.QUOTE);
        PoemResponse todayPoem = poemService.findTodayPoem(quotes);
        model.addAttribute("todayPoem", todayPoem);
        return "index";
    }

    @GetMapping("/writings/diary")
    public String diaryList(Model model) {
        model.addAttribute("writings", writingService.findByType(WritingType.DIARY));
        model.addAttribute("pageTitle", "내 글");
        model.addAttribute("pageSubtitle", "나의 기록들");
        return "writing/list";
    }

    @GetMapping("/writings/quote")
    public String quoteList(Model model) {
        model.addAttribute("writings", writingService.findByType(WritingType.QUOTE));
        model.addAttribute("pageTitle", "좋아하는 글");
        model.addAttribute("pageSubtitle", "마음에 담아둔 문장들");
        return "writing/list";
    }

    @GetMapping("/writings/new")
    public String newForm() {
        return "writing/form";
    }

    @PostMapping("/writings")
    public String create(@RequestParam String content,
                         @RequestParam WritingType type,
                         @RequestParam(required = false) List<String> tags) {
        String joinedTags = (tags != null) ? String.join(",", tags) : null;
        writingService.save(content, type, joinedTags);
        return type == WritingType.DIARY ? "redirect:/writings/diary" : "redirect:/writings/quote";
    }

    @GetMapping("/writings/{id}")
    public String detail(@PathVariable Long id, Model model) {
        WritingResponse writing = writingService.findById(id);
        model.addAttribute("writing", writing);
        model.addAttribute("recommendedPoems", poemService.findByTagsRanked(writing.getTags()));
        return "writing/detail";
    }

    @PostMapping("/writings/{id}/delete")
    public String delete(@PathVariable Long id) {
        WritingResponse writing = writingService.findById(id);
        writingService.delete(id);
        return writing.getType() == WritingType.DIARY ? "redirect:/writings/diary" : "redirect:/writings/quote";
    }
}