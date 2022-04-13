package com.addresstest.service;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;
import com.addresstest.exception.NotFoundAddressException;
import com.addresstest.mapper.AddressMapper;
import com.addresstest.reposirory.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private static final String NOT_FOUND_ADDRESS_MESSAGE = "There is no address with ID = %d in database.";

    private final AddressMapper addressMapper;

    private final AddressRepository addressRepository;

    @Override
    public AddressDto findOrInsertAddress(AddressDto addressDto) {
        AddressEntity addressEntity = addressRepository.findOrInsertAddress(addressMapper.toEntity(addressDto));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public AddressDto getAddressById(long addressId) {
        AddressEntity addressEntity = addressRepository.getAddressById(addressId)
                .orElseThrow(() -> new NotFoundAddressException(String.format(NOT_FOUND_ADDRESS_MESSAGE, addressId)));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public int deleteAddressById(long addressId) {
        addressRepository.getAddressById(addressId)
                .orElseThrow(() -> new NotFoundAddressException(String.format(NOT_FOUND_ADDRESS_MESSAGE, addressId)));
        return addressRepository.deleteAddressById(addressId);
    }
}
