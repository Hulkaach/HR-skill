package com.hulkaach.hr_skill_ninja.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CandidateSaveHistoryEntity {
    private UUID id;
    private UUID candidateId;
    private LocalDateTime createdDateTime;
}
