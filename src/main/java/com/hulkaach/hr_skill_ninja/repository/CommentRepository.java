package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(UUID id);

    List<Candidate> findAll();

    List<Comment> findAllByCandidate(Candidate candidate);
}
