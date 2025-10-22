package com.hulkaach.hr_skill_ninja.dto;

import com.hulkaach.hr_skill_ninja.model.CandidateStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusRequest {
    private CandidateStatus status;
}
