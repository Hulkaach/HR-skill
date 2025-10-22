package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.CandidateStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CandidateRepository {
    Candidate save(Candidate candidate);

    Candidate update(Candidate candidate);

    Optional<Candidate> findById(UUID id);

    List<Candidate> findAll();

    List<Candidate> search(String fio, Set<CandidateStatus> statuses, String position);
}
