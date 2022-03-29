package com.addresstest.validators;

import com.addresstest.dto.AddressDto;
import com.addresstest.exception.MaxStringFieldValueExceedException;
import org.springframework.stereotype.Component;

@Component
public class AddressDtoValidatorImpl implements AddressDtoValidator{

    private static final int MAX_VARCHAR_LENGTH = 50;

    @Override
    public void validate(AddressDto addressDto) {
        if (addressDto == null) {
            throw new NullPointerException("Address dto is null.");
        }
        if (addressDto.getAddress().length() > MAX_VARCHAR_LENGTH) {
            throw new MaxStringFieldValueExceedException("Exceeded maximum value for string field address. " +
                     " Value: " + addressDto.getAddress() + ". " +
                     "Value length = " +addressDto.getAddress().length());
        }
    }
}
