package dev.miguel.backendmobile.utils;

import dev.miguel.backendmobile.domain.dto.MobileDTO;
import dev.miguel.backendmobile.domain.entity.MobileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MobileMapper {

    MobileEntity dtoToEntity(MobileDTO mobileDTO);
    MobileDTO entityToDto(MobileEntity mobileEntity);

}
