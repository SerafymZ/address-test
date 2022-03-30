package com.addresstest.reposirory;

import com.addresstest.entity.AddressEntity;

import java.util.Optional;

public interface AddressRepository {
    AddressEntity findOrInsertAddress(AddressEntity addressEntity);
    Optional<AddressEntity> getAddressById(long addressId);
    int deleteAddressById(long addressId);
}
