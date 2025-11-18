package com.hulkaach.hr_skill_ninja.controller;

import com.hulkaach.hr_skill_ninja.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addCommentToCandidate(@PathVariable UUID candidateId, @RequestBody CommentCreateRepository request) {
        return commentService.addCommentToCandidate(candidateId, request.getAuthor, request.getComment);
    }

    @GetMapping
    public List<CommentDto> getCommentsOfCandidate(@PathVariable UUID candidateId) {
        return commentService.findAllByCandidateId(candidateId);
    }
}
