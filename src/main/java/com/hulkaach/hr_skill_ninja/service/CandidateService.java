package com.hulkaach.hr_skill_ninja.service;

import com.hulkaach.hr_skill_ninja.dto.*;
import com.hulkaach.hr_skill_ninja.exception.CustomExceptionChecked;
import com.hulkaach.hr_skill_ninja.model.CandidateStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CandidateService {
    CandidateDTO create(CreateCandidateRequest request) throws CustomExceptionChecked;

    CandidateDTO update(UUID id, UpdateCandidateRequest request);

    CandidateDTO changeStatus(UUID id, ChangeStatusRequest request);

    CandidateDTO changeComment(UUID id, ChangeCommentRequest request);

    List<CandidateDTO> findAll();

    CandidateDTO findById(UUID id);

    List<CandidateDTO> search(String fio, Set<CandidateStatus> statuses);

    void deletebyId(UUID id);
}
