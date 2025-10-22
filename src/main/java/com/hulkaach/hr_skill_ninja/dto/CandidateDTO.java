package com.hulkaach.hr_skill_ninja.dto;

import com.hulkaach.hr_skill_ninja.model.CandidateStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CandidateDTO {
    private UUID id;
    private String fio;
    private short age;
    private String position;
    private String cvInfo;
    private String comment;
    private CandidateStatus status;
}