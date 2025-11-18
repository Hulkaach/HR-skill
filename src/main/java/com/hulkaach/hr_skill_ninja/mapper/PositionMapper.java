package com.hulkaach.hr_skill_ninja.mapper;

import com.hulkaach.hr_skill_ninja.dto.PositionCreateRequest;
import com.hulkaach.hr_skill_ninja.dto.PositionDto;
import com.hulkaach.hr_skill_ninja.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    PositionDto toDTO(Position position);

    @Mapping(target = "id", ignore = true)
    Position toEntity(PositionCreateRequest positionCreateRequest);
}