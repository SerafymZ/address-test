package com.addresstest.reposirory;

import com.addresstest.entity.AddressEntity;

public interface AddressRepository {
    AddressEntity saveAddress(AddressEntity addressEntity);
    AddressEntity getAddressById(long addressId);
    AddressEntity updateAddress(AddressEntity addressDto);
    int deleteAddressById(long addressId);
}
