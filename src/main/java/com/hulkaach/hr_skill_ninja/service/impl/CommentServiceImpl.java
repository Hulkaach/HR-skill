package com.hulkaach.hr_skill_ninja.service.impl;

import com.hulkaach.hr_skill_ninja.dto.CommentDto;
import com.hulkaach.hr_skill_ninja.mapper.CommentMapper;
import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.Comment;
import com.hulkaach.hr_skill_ninja.repository.CandidateRepository;
import com.hulkaach.hr_skill_ninja.repository.CommentRepository;
import com.hulkaach.hr_skill_ninja.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CandidateRepository candidateRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto addCommentToCandidate(UUID candidateId, String author, String commentText) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalArgumentException("Candidate not found"));
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setAuthor(author);
        comment.setComment(commentText);
        comment.setCreatedDatetime(LocalDateTime.now()); //FIXED_DATETIME
        comment.setCandidate(candidate);
        return commentMapper.toDTO(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(UUID id) {
//        return commentRepository.findById(id).map(commentMapper.toDTO());
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findAllByCandidateId(UUID candidateId) {
        return commentRepository.findAllByCandidateId(candidateId)
                .stream()
                .map(commentMapper::toDTO)
                .toList();
    }
}
