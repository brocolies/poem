package com.poem.poem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private Integer publishedYear;

    private String purchaseUrl;

    private String coverImageUrl;

    public Book(String title, String author, Integer publishedYear, String purchaseUrl, String coverImageUrl) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.purchaseUrl = purchaseUrl;
        this.coverImageUrl = coverImageUrl;
    }
}
