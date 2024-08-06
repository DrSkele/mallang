package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;

@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question")
    private Question question;

    private String word;
    private String mean;
}