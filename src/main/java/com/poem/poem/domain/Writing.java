package com.poem.poem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Writing {
    // 엔티티: JPA가 이 내용을 보고 DB에 WRITING 테이블을 만들어줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WritingType type;
    private String tags;
    // tags: 나중에 큐레이팅할 때 매칭하기 위함

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Writing(String content, WritingType type, String tags) {
        this.content = content;
        this.type = type;
        this.tags = tags;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}