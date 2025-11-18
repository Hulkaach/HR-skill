package com.hulkaach.hr_skill_ninja.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_datetime", nullable = false)
    private LocalDateTime createdDatetime;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}