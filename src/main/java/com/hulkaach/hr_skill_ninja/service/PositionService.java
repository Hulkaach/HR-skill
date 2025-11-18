package com.hulkaach.hr_skill_ninja.service;

import com.hulkaach.hr_skill_ninja.dto.PositionCreateRequest;
import com.hulkaach.hr_skill_ninja.dto.PositionDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;
import java.util.UUID;

public interface PositionService {
    PositionDto addPosition(PositionCreateRequest request);

    void addCandidateToPosition(UUID positionId, UUID candidateId);

    Optional<Object> getPositionWithCandidates(UUID positionId);

    void archivePosition(UUID positionId);
}