package com.hulkaach.hr_skill_ninja.service;

import com.hulkaach.hr_skill_ninja.dto.CandidateDTO;
import com.hulkaach.hr_skill_ninja.model.Candidate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    CandidateDTO toDTO(Candidate candidate);
}
