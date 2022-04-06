package com.addresstest.controller;

import com.addresstest.dto.AddressDto;
import com.addresstest.dto.basedto.ResponseDto;
import com.addresstest.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ResponseDto<AddressDto>> findOrInsertAddress(@Valid @RequestBody AddressDto addressDto) {
        AddressDto addressResult = addressService.findOrInsertAddress(addressDto);
        return ResponseEntity.ok(ResponseDto.okResponseDto(addressResult));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ResponseDto<AddressDto>> getAddressById(@PathVariable @Min(1) Long addressId) {
        AddressDto addressResult = addressService.getAddressById(addressId);
        return ResponseEntity.ok(ResponseDto.okResponseDto(addressResult));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ResponseDto<Integer>> deleteAddressById(@PathVariable @Min(1) Long addressId) {
        int deleteResult = addressService.deleteAddressById(addressId);
        return ResponseEntity.ok(ResponseDto.okResponseDto(deleteResult));
    }
}
