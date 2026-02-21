package com.poem.poem.service;

import com.poem.poem.domain.Poem;
import com.poem.poem.repository.PoemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}