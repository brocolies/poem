package com.poem.poem.service;

import com.poem.poem.domain.Writing;
import com.poem.poem.domain.WritingType;
import com.poem.poem.repository.WritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // lombok이 생성자 자동 생성, 자동 주입
public class WritingService {
    private final WritingRepository writingRepository;

    public Writing save(String content, WritingType type, String tags) {
        Writing writing = new Writing(content, type, tags);
        return writingRepository.save(writing);
    }

    public List<Writing> findAll() {
        return writingRepository.findAll();
    }

    public Writing findById(Long id) {
        return writingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다. id = " + id));
    }

    public void delete(Long id) {
        writingRepository.deleteById(id);
    }

    public List<Writing> findByType(WritingType type) {
        return writingRepository.findByType(type);
    }
}
