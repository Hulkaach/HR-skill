package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.Position;
import com.hulkaach.hr_skill_ninja.model.PositionStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PositionHibernateRepositoryImpl implements PositionRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Position save(Position position) {
        log.info("Save position via JPA");
        entityManager.merge(position);
        return position;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Position> findById(UUID id) {
        log.info("Find position by Id via JPA");
        Position position = entityManager.find(Position.class, id);
        return Optional.ofNullable(position);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> findAll(UUID id) {
        log.info("Find all position via JPA");
        TypedQuery<Position> positionQuery = entityManager.createQuery("FROM Position", Position.class);
        return positionQuery.getResultList();
    }

    @Override
    @Transactional
    public void addCandidate(UUID positionId, UUID candidateId) {
        log.info("Add candidate to position via JPA");
        Position position = entityManager.find(Position.class, positionId);
        Candidate candidate = entityManager.find(Candidate.class, candidateId);

        if (position != null && candidate != null) {
            position.getCandidates().add(candidate);
        }
    }

    @Override
    @Transactional
    public void archive(UUID positionId) {
        log.info("Archive position via JPA");
        Position position = entityManager.find(Position.class, positionId);

        if (position != null) {
            position.setStatus(PositionStatus.ARCHIVE);
        }
    }
}