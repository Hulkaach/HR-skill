package com.hulkaach.hr_skill_ninja.service;

import com.hulkaach.hr_skill_ninja.dto.PositionDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PositionService {
    PositionDto addPosition(String name, String description);

    void addCandidateToPosition(UUID positionId, UUID candidateId);

    Optional<PositionDto> getPositionWithCandidates(UUID positionId);

    void archivePosition(UUID positionId);

    @Transactional
    List<PositionDto> findAll();
}