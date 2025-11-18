package com.hulkaach.hr_skill_ninja.service.impl;

import com.hulkaach.hr_skill_ninja.dto.PositionDto;
import com.hulkaach.hr_skill_ninja.mapper.PositionMapper;
import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.Position;
import com.hulkaach.hr_skill_ninja.model.PositionStatus;
import com.hulkaach.hr_skill_ninja.repository.CandidateRepository;
import com.hulkaach.hr_skill_ninja.repository.PositionRepository;
import com.hulkaach.hr_skill_ninja.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public PositionDto addPosition(String name, String description) {
        Position position = new Position();
        position.setId(UUID.randomUUID());
        position.setName(name);
        position.setDescription(description);
        position.setStatus(PositionStatus.ACTIVE);
        position.setCreatedDate(LocalDate.now());
        return positionMapper.toDTO(positionRepository.save(position));
    }

    @Override
    @Transactional
    public void addCandidateToPosition(UUID positionId, UUID candidateId) {
        Position position = positionRepository.findById(positionId).orElseThrow();
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();

        position.getCandidates().add(candidate);

        positionRepository.save(position);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PositionDto> getPositionWithCandidates(UUID positionId) {
        return positionRepository.findById(positionId).map(positionMapper::toDTO);
    }

    @Override
    @Transactional
    public void archivePosition(UUID positionId) {
        Position position = positionRepository.findById(positionId).orElseThrow();
        position.setStatus(PositionStatus.ARCHIVE);
        positionRepository.save(position);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionDto> findAll() {
        return positionRepository.findAll().stream().map(positionMapper::toDTO).collect(Collectors.toList());
    }
}