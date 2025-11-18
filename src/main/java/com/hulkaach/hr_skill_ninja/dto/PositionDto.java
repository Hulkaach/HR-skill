package com.hulkaach.hr_skill_ninja.dto;

import com.hulkaach.hr_skill_ninja.model.PositionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PositionDto {
    private UUID id;
    private String name;
    private String description;
    private PositionStatus status;
    private LocalDate createDate;
}
