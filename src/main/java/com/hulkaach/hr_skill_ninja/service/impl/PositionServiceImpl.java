package com.hulkaach.hr_skill_ninja.service.impl;

import com.hulkaach.hr_skill_ninja.dto.PositionCreateRequest;
import com.hulkaach.hr_skill_ninja.dto.PositionDto;
import com.hulkaach.hr_skill_ninja.service.PositionService;

import java.util.UUID;

public class PositionServiceImpl implements PositionService {
    @Override
    public PositionDto addPosition(PositionCreateRequest request) {
        return null;
    }

    @Override
    public void addCandidateToPosition(UUID positionId, UUID candidateId) {

    }
}

//@Override
//public CandidateDTO create(CreateCandidateRequest request) throws CustomExceptionChecked {
//    Candidate candidate = new Candidate();
//
//    candidate.setFio(request.getFio());
//    candidate.setAge(request.getAge());
//    candidate.setPosition(request.getPosition());
//    candidate.setCvInfo(request.getCvInfo());
//    candidate.setStatus(CandidateStatus.NEW);
//
//    return mapper.toDTO(repository.save(candidate));
//}
