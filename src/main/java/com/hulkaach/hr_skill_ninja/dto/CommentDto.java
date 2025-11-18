package com.hulkaach.hr_skill_ninja.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CommentDto {
    private UUID id;
    private String author;
    private String comment;
    private LocalDateTime createdDatetime;
}
