package com.poem.poem.domain;

public enum Tag {
    사랑("사랑"),
    외로움("외로움"),
    슬픔("슬픔"),
    그리움("그리움"),
    불안("불안"),
    희망("희망"),
    절망("절망"),
    설렘("설렘"),
    허무("허무"),
    후회("후회");

    private final String label;

    Tag(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}