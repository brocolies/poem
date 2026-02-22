package com.poem.poem.dto;

import com.poem.poem.domain.Book;
import lombok.Getter;

@Getter
public class BookResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final Integer publishedYear;
    private final String purchaseUrl;
    private final String coverImageUrl;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishedYear = book.getPublishedYear();
        this.purchaseUrl = book.getPurchaseUrl();
        this.coverImageUrl = book.getCoverImageUrl();
    }
}