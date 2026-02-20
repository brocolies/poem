package com.poem.poem.repository;

import com.poem.poem.domain.Poem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoemRepository extends JpaRepository<Poem, Long> {
    List<Poem> findByBookId(Long bookId);
}

