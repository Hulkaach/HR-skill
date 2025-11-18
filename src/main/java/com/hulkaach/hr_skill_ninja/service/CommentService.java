package com.hulkaach.hr_skill_ninja.service;

import com.hulkaach.hr_skill_ninja.dto.CommentDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    CommentDto addCommentToCandidate(UUID candidateId, String author, String commentText);

    Optional<CommentDto> findById(UUID id);

    List<CommentDto> findAllCandidateId(UUID candidateId);
}
