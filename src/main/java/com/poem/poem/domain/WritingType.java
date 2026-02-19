package com.poem.poem.domain;

public enum WritingType {
    // 글의 종류 코드 레벨에서 강제 -> 무조건 글은 두가지 밖에 안됨 내가 쓴 글이거나 아카이빙한 글이거나
    DIARY,  // 내가 쓴 글
    QUOTE   // 좋아하는 글 아카이빙
}