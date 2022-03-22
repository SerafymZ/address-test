package com.addresstest.service;

import com.addresstest.dto.AddressDto;
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
        var result = addressRepository.saveAddress(addressMapper.toEntity(addressDto));
        return addressMapper.toDto(result);
    }

    @Override
    public AddressDto getAddressById(long addressId) {
        var result = addressRepository.getAddressById(addressId);
        return addressMapper.toDto(result);
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto) {
        var result = addressRepository.updateAddress(addressMapper.toEntity(addressDto));
        return addressMapper.toDto(result);
    }

    @Override
    public int deleteAddressById(long addressId) {
        return addressRepository.deleteAddressById(addressId);
    }
}
