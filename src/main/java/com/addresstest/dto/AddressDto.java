package com.addresstest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class AddressDto {
    private Long id;

    @NotBlank
    @Size(max = 50, message = "{address.field.address.exceeded-size}")
    private String address;
}
