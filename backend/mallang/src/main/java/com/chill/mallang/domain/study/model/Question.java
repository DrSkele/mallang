package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;

public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private StudyGame studyGame;

    @OneToMany
    private Problem problem;

}
