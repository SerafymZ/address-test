package com.addresstest.mapper;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;

public interface AddressMapper {
    AddressEntity toEntity(AddressDto addressDto);
    AddressDto toDto(AddressEntity addressEntity);
}
