package com.addresstest.controller;

import com.addresstest.dto.AddressDto;
import com.addresstest.dto.basedto.ResponseDto;
import com.addresstest.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity<ResponseDto<AddressDto>> saveAddress(@RequestBody AddressDto addressDto) {
        var addressResult = addressService.saveAddress(addressDto);
        return ResponseEntity.ok(ResponseDto.okResponseDto(addressResult)) ;
    }

    @GetMapping("/{addressId}")
    public ResponseDto<AddressDto> getAddressById(@PathVariable long addressId) {
        var addressResult = addressService.getAddressById(addressId);
        return new ResponseDto<>(addressResult);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<AddressDto>> updateAddress(@RequestBody AddressDto addressDto) {
        var addressResult = addressService.updateAddress(addressDto);
        return ResponseEntity.ok(ResponseDto.okResponseDto(addressResult)) ;
    }

    @DeleteMapping("/{addressId}")
    public ResponseDto<Integer> deleteAddressById(@PathVariable long addressId) {
        var addressResult = addressService.deleteAddressById(addressId);
        return new ResponseDto<>(addressResult);
    }
}
