package com.hulkaach.hr_skill_ninja.mapper;

import com.hulkaach.hr_skill_ninja.dto.CommentCreateRequest;
import com.hulkaach.hr_skill_ninja.dto.CommentDto;
import com.hulkaach.hr_skill_ninja.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDTO(Comment comment);

    @Mapping(target = "id", ignore = true)
    Comment toEntity(CommentCreateRequest commentCreateRequest);
}