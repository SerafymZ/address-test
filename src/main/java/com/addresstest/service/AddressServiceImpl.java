package com.addresstest.service;

import com.addresstest.dto.AddressDto;
import com.addresstest.exception.NotFoundAddressException;
import com.addresstest.mapper.AddressMapper;
import com.addresstest.reposirory.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService{

    private final AddressMapper addressMapper;

    private final AddressRepository addressRepository;

    @Override
    public AddressDto saveAddress(AddressDto addressDto) {
        var addressEntity = addressRepository.saveAddress(addressMapper.toEntity(addressDto));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public AddressDto getAddressById(long addressId) {
        var addressEntity = addressRepository.getAddressById(addressId)
                .orElseThrow(() -> new NotFoundAddressException("There is no address with ID = " + addressId + " in database."));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public AddressDto updateAddress(long addressId, AddressDto addressDto) {
        getAddressById(addressId);
        var addressEntity = addressRepository.updateAddress(addressMapper.toEntity(addressDto));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public int deleteAddressById(long addressId) {
        getAddressById(addressId);
        int deleteResult = addressRepository.deleteAddressById(addressId);
        if (deleteResult == 0) {
            throw new NotFoundAddressException("There is no address with ID = " + addressId + " in database.");
        }
        return deleteResult;
    }
}
