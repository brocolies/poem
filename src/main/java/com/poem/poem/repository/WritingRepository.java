package com.poem.poem.repository;

import com.poem.poem.domain.Writing;
import com.poem.poem.domain.WritingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WritingRepository extends JpaRepository<Writing, Long> {
    // Writing entity에 대해 PK: Long
    // extends JpaRepository: 기본 CRUD가 제공 - save(), findAll(), findById(), deleteById() 등
    List<Writing> findByType(WritingType type);

}
