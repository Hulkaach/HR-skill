package com.hulkaach.hr_skill_ninja.service;

import com.hulkaach.hr_skill_ninja.dto.CandidateDTO;
import com.hulkaach.hr_skill_ninja.dto.CreateCandidateRequest;
import com.hulkaach.hr_skill_ninja.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    CandidateDTO toDTO(Candidate candidate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "comment", ignore = true)
    Candidate toEntity(CreateCandidateRequest createCandidateRequest);
}
