package com.poem.poem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Poem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String tags;

    @ManyToOne(fetch = FetchType.LAZY)
    // 여러 시가 하나의 시집에 속한다는 뜻, book_id라는 FK 생성
    @JoinColumn(name = "book_id")
    private Book book;

    public Poem(String title, String author, String content, String tags, Book book) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.tags = tags;
        this.book = book;
    }
}