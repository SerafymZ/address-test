package com.addresstest.controller;

import com.addresstest.dto.AddressDto;
import com.addresstest.dto.basedto.ResponseDto;
import com.addresstest.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ResponseDto<AddressDto>> findOrInsertAddress(@RequestBody AddressDto addressDto) {
        AddressDto addressResult = addressService.findOrInsertAddress(addressDto);
        return ResponseEntity.ok(ResponseDto.okResponseDto(addressResult)) ;
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ResponseDto<AddressDto>> getAddressById(@PathVariable Long addressId) {
        AddressDto addressResult = addressService.getAddressById(addressId);
        return ResponseEntity.ok(ResponseDto.okResponseDto(addressResult));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ResponseDto<Integer>> deleteAddressById(@PathVariable Long addressId) {
        int deleteResult = addressService.deleteAddressById(addressId);
        return ResponseEntity.ok(ResponseDto.okResponseDto(deleteResult));
    }
}
