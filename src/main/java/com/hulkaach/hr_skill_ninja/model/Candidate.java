package com.hulkaach.hr_skill_ninja.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@Getter
@Setter
public class Candidate {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "age", nullable = false)
    private short age;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "cv_info")
    private String cvInfo;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;

    @ManyToMany(
            mappedBy = "candidates",
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Set<Position> positions = new HashSet<>();

    @OneToMany(
            mappedBy = "candidate",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();

    public String getStatusString() {
        return status.name();
    }
}