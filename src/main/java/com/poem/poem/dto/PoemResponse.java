package com.poem.poem.dto;

import com.poem.poem.domain.Poem;
import lombok.Getter;

@Getter
public class PoemResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final String content;
    private final String tags;
    private final Long bookId;
    private final String bookTitle;

    public PoemResponse(Poem poem) {
        this.id = poem.getId();
        this.title = poem.getTitle();
        this.author = poem.getAuthor();
        this.content = poem.getContent();
        this.tags = poem.getTags();
        this.bookId = poem.getBook() != null ? poem.getBook().getId() : null;
        this.bookTitle = poem.getBook() != null ? poem.getBook().getTitle() : null;
    }
}