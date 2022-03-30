package com.addresstest.service;

import com.addresstest.dto.AddressDto;

public interface AddressService {
    AddressDto findOrInsertAddress(AddressDto addressDto);
    AddressDto getAddressById(long addressId);
    int deleteAddressById(long addressId);
}
