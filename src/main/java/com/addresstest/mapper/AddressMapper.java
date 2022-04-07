package com.addresstest.mapper;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AddressMapper {
    AddressEntity toEntity(AddressDto addressDto);
    AddressDto toDto(AddressEntity addressEntity);
}
