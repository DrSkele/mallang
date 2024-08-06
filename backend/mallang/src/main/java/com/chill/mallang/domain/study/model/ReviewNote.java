package com.chill.mallang.domain.study.model;

import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class ReviewNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private StudyGameLog studyGameLog;

    @OneToOne
    private Word word;

    private LocalDateTime created_at;
}