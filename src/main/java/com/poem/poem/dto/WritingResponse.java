package com.poem.poem.dto;

import com.poem.poem.domain.Writing;
import com.poem.poem.domain.WritingType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WritingResponse {

    private final Long id;
    private final String content;
    private final WritingType type;
    private final String tags;
    private final LocalDateTime createdAt;

    public WritingResponse(Writing writing) {
        this.id = writing.getId();
        this.content = writing.getContent();
        this.type = writing.getType();
        this.tags = writing.getTags();
        this.createdAt = writing.getCreatedAt();
    }
}