package com.addresstest.service;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;
import com.addresstest.exception.NotFoundAddressException;
import com.addresstest.mapper.AddressMapper;
import com.addresstest.reposirory.AddressRepository;
import com.addresstest.validators.AddressDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private static final String NO_ADDRESS_MESSAGE_PART_1 = "There is no address with ID = ";
    private static final String NO_ADDRESS_MESSAGE_PART_2 = " in database.";

    private final AddressMapper addressMapper;

    private final AddressRepository addressRepository;

    private final AddressDtoValidator addressDtoValidator;

    @Override
    public AddressDto findOrInsertAddress(AddressDto addressDto) {
        addressDtoValidator.validate(addressDto);
        AddressEntity addressEntity = addressRepository.findOrInsertAddress(addressMapper.toEntity(addressDto));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public AddressDto getAddressById(long addressId) {
        AddressEntity addressEntity = addressRepository.getAddressById(addressId)
                .orElseThrow(() -> new NotFoundAddressException(
                        NO_ADDRESS_MESSAGE_PART_1 + addressId + NO_ADDRESS_MESSAGE_PART_2
                ));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public AddressDto findOrUpdateAddress(long addressId, AddressDto addressDto) {
        addressDtoValidator.validate(addressDto);
        addressRepository.getAddressById(addressId)
                .orElseThrow(() -> new NotFoundAddressException(
                        NO_ADDRESS_MESSAGE_PART_1 + addressId + NO_ADDRESS_MESSAGE_PART_2
                ));
        AddressEntity addressEntity = addressRepository.findOrUpdateAddress(addressMapper.toEntity(addressDto));
        return addressMapper.toDto(addressEntity);
    }

    @Override
    public int deleteAddressById(long addressId) {
        addressRepository.getAddressById(addressId)
                .orElseThrow(() -> new NotFoundAddressException(
                        NO_ADDRESS_MESSAGE_PART_1 + addressId + NO_ADDRESS_MESSAGE_PART_2
                ));
        int deleteResult = addressRepository.deleteAddressById(addressId);
        if (deleteResult == 0) {
            throw new NotFoundAddressException(NO_ADDRESS_MESSAGE_PART_1 + addressId + NO_ADDRESS_MESSAGE_PART_2);
        }
        return deleteResult;
    }
}
