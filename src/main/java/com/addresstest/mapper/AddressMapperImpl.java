package com.addresstest.mapper;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements AddressMapper{

    @Override
    public AddressEntity toEntity(AddressDto addressDto) {
        if(addressDto == null) {
            return null;
        }

        AddressEntity addressEntity = new AddressEntity();
        if(addressDto.getId() != null) {
            addressEntity.setId(addressDto.getId());
        }

        if(addressDto.getAddress() != null) {
            addressEntity.setAddress(addressDto.getAddress());
        }

        return addressEntity;
    }

    @Override
    public AddressDto toDto(AddressEntity addressEntity) {
        if(addressEntity == null) {
            return null;
        }
        AddressDto addressDto = new AddressDto();
        if(addressEntity.getId() != null) {
            addressDto.setId(addressEntity.getId());
        }
        if(addressEntity.getAddress() != null) {
            addressDto.setAddress(addressEntity.getAddress());
        }
        return addressDto;
    }
}
