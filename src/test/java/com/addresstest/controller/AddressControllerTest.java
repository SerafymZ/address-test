package com.addresstest.controller;

import com.addresstest.dto.AddressDto;
import com.addresstest.dto.basedto.ResponseDto;
import com.addresstest.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class AddressControllerTest {

    private static final String PATH_WITH_ID = "/address/{id}";
    private static final String PATH = "/address/";
    private static final String ADDRESS = "Canada";
    private static final long ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AddressService addressService;

    @Test
    void findOrInsertAddress_shouldBeSuccessFindOrInsert() throws Exception {
        //given
        var addressDto = createAddressDto(null, ADDRESS);
        var addressDtoResult = createAddressDto(ID, ADDRESS);
        when(addressService.findOrInsertAddress(addressDto)).thenReturn(addressDtoResult);

        //when
        mockMvc.perform(
                        post(PATH)
                                .content(objectMapper.writeValueAsString(addressDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ResponseDto.okResponseDto(addressDtoResult))));

        //then
        verify(addressService, times(1)).findOrInsertAddress(addressDto);
    }

    @Test
    void getAddressById_shouldBeReturnedAddressDto() throws Exception {
        //given
        var addressDto = createAddressDto(ID, ADDRESS);

        when(addressService.getAddressById(ID)).thenReturn(addressDto);

        //when
        mockMvc.perform(
                        get(PATH_WITH_ID, ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ResponseDto.okResponseDto(addressDto))));

        //then
        verify(addressService, times(1)).getAddressById(1);
    }

    @Test
    void deleteAddressById_shouldBeDeleteSuccessful() throws Exception {
        //given
        var result = 1;
        when(addressService.deleteAddressById(ID)).thenReturn(result);

        //when
        mockMvc.perform(
                        delete(PATH_WITH_ID, ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(result));

        //then
        verify(addressService, times(1)).deleteAddressById(ID);
    }

    private AddressDto createAddressDto(Long id, String address) {
        var result = new AddressDto();
        result.setId(id);
        result.setAddress(address);
        return result;
    }
}