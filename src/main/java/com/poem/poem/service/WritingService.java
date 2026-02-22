package com.poem.poem.service;

import com.poem.poem.domain.Writing;
import com.poem.poem.domain.WritingType;
import com.poem.poem.dto.WritingResponse;
import com.poem.poem.repository.WritingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WritingService {

    private final WritingRepository writingRepository;

    public Writing save(String content, WritingType type, String tags) {
        Writing writing = new Writing(content, type, tags);
        return writingRepository.save(writing);
    }

    public List<WritingResponse> findAll() {
        return writingRepository.findAll().stream()
                .map(WritingResponse::new)
                .toList();
    }

    public WritingResponse findById(Long id) {
        Writing writing = writingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글을 찾을 수 없습니다. id=" + id));
        return new WritingResponse(writing);
    }

    public List<WritingResponse> findByType(WritingType type) {
        return writingRepository.findByType(type).stream()
                .map(WritingResponse::new)
                .toList();
    }

    public void delete(Long id) {
        writingRepository.deleteById(id);
    }
}