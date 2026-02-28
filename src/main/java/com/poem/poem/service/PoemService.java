package com.poem.poem.service;

import com.poem.poem.domain.Poem;
import com.poem.poem.domain.Writing;
import com.poem.poem.dto.PoemResponse;
import com.poem.poem.repository.PoemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

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
                .toList();

        // 겹치는 개수 같을 때 랜덤으로 셔필
        // 겹침 수 기준으로 그룹핑 → 같은 점수 내에서만 셔플
        Map<Long, List<Poem>> grouped = new LinkedHashMap<>();
        for (Poem poem : matched) {
            long count = tagList.stream().filter(tag -> poem.getTags().contains(tag)).count();
            grouped.computeIfAbsent(count, k -> new ArrayList<>()).add(poem);
        }

        List<Poem> result = new ArrayList<>();
        grouped.keySet().stream()
                .sorted(Comparator.reverseOrder())  // 겹침 많은 순
                .forEach(key -> {
                    List<Poem> group = grouped.get(key);
                    Collections.shuffle(group);  // 같은 점수 내에서만 셔플
                    result.addAll(group);
                });

        return result;
    }
    public PoemResponse findTodayPoem(List<Writing> quotes) {
        if (quotes.isEmpty()) {
            return null;
        }

        // 1. QUOTE 태그 전부 집계
        Map<String, Long> tagCount = quotes.stream()
                .filter(q -> q.getTags() != null && !q.getTags().isBlank())
                .flatMap(q -> Arrays.stream(q.getTags().split(",")))
                .map(String::trim)
                .collect(Collectors.groupingBy(t -> t, Collectors.counting()));

        if (tagCount.isEmpty()) {
            return null;
        }

        // 2. 상위 3개 태그 추출
        List<String> topTags = tagCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        // 3. 상위 3개 중 랜덤 1개 선택
        Random random = new Random();
        String selectedTag = topTags.get(random.nextInt(topTags.size()));

        // 4. 해당 태그의 시 중 랜덤 1편
        List<Poem> matched = poemRepository.findAll().stream()
                .filter(poem -> poem.getTags() != null && poem.getTags().contains(selectedTag))
                .toList();

        if (matched.isEmpty()) {
            return null;
        }

        Poem selected = matched.get(random.nextInt(matched.size()));
        return new PoemResponse(selected);
    }
}