package com.addresstest.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddressDto {
    private Long id;

    @NotBlank
    @Size(max = 50, message = "Address size must be less than 50 symbols.")
    private String address;
}
