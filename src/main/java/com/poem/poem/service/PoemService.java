package com.poem.poem.service;

import com.poem.poem.domain.Poem;
import com.poem.poem.repository.PoemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoemService {

    private final PoemRepository poemRepository;

    public List<Poem> findAll() {
        return poemRepository.findAll();
    }

    public Poem findById(Long id) {
        return poemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("시를 찾을 수 없습니다. id=" + id));
    }

    public List<Poem> findByBookId(Long bookId) {
        return poemRepository.findByBookId(bookId);
    }

    public List<Poem> findByTagsRanked(String tags) {
        if (tags == null || tags.isBlank()) {
            return List.of();
        }

        List<String> tagList = Arrays.asList(tags.split(","));
        List<Poem> allPoems = poemRepository.findAll();

        List<Poem> matched = allPoems.stream()
                .filter(poem -> poem.getTags() != null)
                .filter(poem -> {
                    for (String tag : tagList) {
                        if (poem.getTags().contains(tag.trim())) {
                            return true;
                        }
                    }
                    return false;
                })
                .sorted((a, b) -> {
                    long countA = tagList.stream().filter(tag -> a.getTags().contains(tag)).count();
                    long countB = tagList.stream().filter(tag -> b.getTags().contains(tag)).count();
                    return Long.compare(countB, countA);  // 겹침 많은 순
                })
                .toList();

        // 겹치는 개수 같을 때 랜덤으로 셔필
        java.util.List<Poem> result = new java.util.ArrayList<>(matched);
        Collections.shuffle(result);
        return result;
    }

}