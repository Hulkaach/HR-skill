package com.hulkaach.hr_skill_ninja.service.impl;

import com.hulkaach.hr_skill_ninja.dto.*;
import com.hulkaach.hr_skill_ninja.exception.CandidateNotFoundException;
import com.hulkaach.hr_skill_ninja.exception.CustomExceptionChecked;
import com.hulkaach.hr_skill_ninja.exception.IllegalStatusTransitionException;
import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.CandidateStatus;
import com.hulkaach.hr_skill_ninja.repository.CandidateRepository;
import com.hulkaach.hr_skill_ninja.repository.CandidateSearchRepository;
import com.hulkaach.hr_skill_ninja.service.CandidateMapper;
import com.hulkaach.hr_skill_ninja.service.CandidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository repository;
    private final CandidateSearchRepository candidateSearchRepository;
    private final CandidateMapper mapper;

    private static final Map<CandidateStatus, Set<CandidateStatus>> ALLOWED_TRANSITIONS = Map.of(
            CandidateStatus.NEW, Set.of(CandidateStatus.CV_REVIEW, CandidateStatus.DECLINED),
            CandidateStatus.CV_REVIEW, Set.of(CandidateStatus.SCHEDULED_FOR_INTERVIEW, CandidateStatus.DECLINED),
            CandidateStatus.SCHEDULED_FOR_INTERVIEW, Set.of(CandidateStatus.INTERVIEW, CandidateStatus.DECLINED),
            CandidateStatus.INTERVIEW, Set.of(CandidateStatus.OFFER, CandidateStatus.DECLINED),
            CandidateStatus.OFFER, Set.of(CandidateStatus.ACCEPTED, CandidateStatus.DECLINED),
            CandidateStatus.ACCEPTED, Set.of(CandidateStatus.STARTED_WORKING, CandidateStatus.DECLINED),
            CandidateStatus.STARTED_WORKING, Set.of(),
            CandidateStatus.DECLINED, Set.of()
    );

    @Override
    @Transactional
    public CandidateDTO create(CreateCandidateRequest request) throws CustomExceptionChecked {
        Candidate candidate = new Candidate();

        candidate.setFio(request.getFio());
        candidate.setAge(request.getAge());
        candidate.setPosition(request.getPosition());
        candidate.setCvInfo(request.getCvInfo());
        candidate.setStatus(CandidateStatus.NEW);

        return mapper.toDTO(repository.save(candidate));
    }

    private void sendInfo(Candidate saved) throws CustomExceptionChecked {
        log.info("Send candidate ID={} info", saved.getId());

        if (!saved.getPosition().contains("Java")) {
            throw new CustomExceptionChecked("Checked exception");
        }
    }

    @Override
    @Transactional
    public CandidateDTO update(UUID id, UpdateCandidateRequest request) {
        Candidate candidate = getCandidateOrThrow(id);

        candidate.setFio(request.getFio());
        candidate.setAge(request.getAge());
        candidate.setPosition(request.getPosition());
        candidate.setCvInfo(request.getCvInfo());

        return mapper.toDTO(repository.save(candidate));
    }

    @Override
    @Transactional
    public CandidateDTO changeStatus(UUID id, ChangeStatusRequest request) {
        Candidate candidate = getCandidateOrThrow(id);

        if (!isValidTransition(candidate.getStatus(), request.getStatus())) {
            throw new IllegalStatusTransitionException(
                    String.format("Cannot transition from %s to %s",
                            candidate.getStatus(), request.getStatus()));
        }

        candidate.setStatus(request.getStatus());
        return mapper.toDTO(repository.save(candidate));
    }

    @Override
    @Transactional
    public CandidateDTO changeComment(UUID id, ChangeCommentRequest request) {
        Candidate candidate = getCandidateOrThrow(id);
        candidate.setComment(request.getComment());
        return mapper.toDTO(repository.save(candidate));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new CandidateNotFoundException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CandidateDTO> search(String fio, Set<CandidateStatus> statuses) {
        return candidateSearchRepository.search(fio, statuses).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletebyId(UUID id) {
        repository.deleteById(id);
    }

    private Candidate getCandidateOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id));
    }

    private boolean isValidTransition(CandidateStatus from, CandidateStatus to) {
        if (to == CandidateStatus.DECLINED) {
            return true;
        }
        Set<CandidateStatus> allowedTargets = ALLOWED_TRANSITIONS.get(from);
        return allowedTargets != null && allowedTargets.contains(to);
    }
}