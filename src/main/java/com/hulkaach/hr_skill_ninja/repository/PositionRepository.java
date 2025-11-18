package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Position;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PositionRepository {
    Position save(Position position);

    Optional<Position> findById(UUID id);

    List<Position> findAll(UUID id);

    void addCandidate(UUID positionId, UUID candidateId);

    void archive(UUID positionId);
}
