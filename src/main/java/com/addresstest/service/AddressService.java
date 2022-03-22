package com.addresstest.service;

import com.addresstest.dto.AddressDto;

public interface AddressService {
    AddressDto saveAddress(AddressDto addressDto);
    AddressDto getAddressById(long addressId);
    AddressDto updateAddress(long addressId, AddressDto addressDto);
    int deleteAddressById(long addressId);
}
