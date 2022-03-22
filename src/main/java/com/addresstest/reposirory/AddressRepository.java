package com.addresstest.reposirory;

import com.addresstest.entity.AddressEntity;

import java.util.Optional;

public interface AddressRepository {
    AddressEntity saveAddress(AddressEntity addressEntity);
    Optional<AddressEntity> getAddressById(long addressId);
    AddressEntity updateAddress(AddressEntity addressDto);
    int deleteAddressById(long addressId);
}
