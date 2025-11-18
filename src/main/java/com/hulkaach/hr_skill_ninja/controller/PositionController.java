package com.hulkaach.hr_skill_ninja.controller;

import com.hulkaach.hr_skill_ninja.dto.PositionCreateRequest;
import com.hulkaach.hr_skill_ninja.dto.PositionDto;
import com.hulkaach.hr_skill_ninja.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PositionDto addPosition(@RequestBody PositionCreateRequest request) {
        return positionService.addPosition(request.getName(), request.getDescription());
    }

    @PutMapping("/{positionId}/candidates/{candidateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCandidateToPosition(@PathVariable UUID positionId, @PathVariable UUID candidateId) {
        positionService.addCandidateToPosition(positionId, candidateId);
    }

    @GetMapping("/{positionId}")
    public PositionDto getPositionWithCandidates(@PathVariable UUID positionId) {
        return positionService.getPositionWithCandidates(positionId)
                .orElseThrow(() -> new IllegalArgumentException("Position not found"));
    }

    @PutMapping("/{positionId}/archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archivePosition(@PathVariable UUID positionId) {
        positionService.archivePosition(positionId);
    }
}
