package com.addresstest.controller;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;
import com.addresstest.initializer.MsSQLServer;
import com.addresstest.mapper.AddressMapper;
import com.addresstest.reposirory.AddressRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = MsSQLServer.Initializer.class)
class AddressControllerIntegrationTest {

    private static final String PATH_WITH_ID = "/address/{id}";
    private static final String PATH = "/address/";
    private static final String ADDRESS = "Canada";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AddressRepositoryImpl repository;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void init() {
        MsSQLServer.container.start();
    }

    @After
    void clearDb() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "address");
    }

    @Test
    void findOrInsertAddress_shouldBeInsertAddressSuccessfully() throws Exception {
        //given
        var addressDto = new AddressDto();
        addressDto.setAddress(ADDRESS);

        //when
        mockMvc.perform(
                        post(PATH)
                                .content(objectMapper.writeValueAsString(addressDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.address").value(ADDRESS));
    }

    @Test
    void findOrInsertAddress_shouldBeFindAddressSuccessfully() throws Exception {
        //given
        var addressDto = new AddressDto();
        addressDto.setAddress(ADDRESS);
        findOrInsertTestAddress(addressDto);

        //when
        mockMvc.perform(
                        post(PATH)
                                .content(objectMapper.writeValueAsString(addressDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.address").value(ADDRESS));

        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        var expectedCountLines = 1;
        Assertions.assertThat(actualCountLines).isEqualTo(expectedCountLines);
    }

    @Test
    void findOrInsertAddress_shouldBeReturnedBadRequest() throws Exception {
        //when
        mockMvc.perform(
                        post(PATH)
                                .content(objectMapper.writeValueAsString(null))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAddressById_shouldBeGetAddressSuccessful() throws Exception {
        //given
        var addressDto = new AddressDto();
        addressDto.setAddress(ADDRESS);

        var id = findOrInsertTestAddress(addressDto).getId();

        //when
        mockMvc.perform(
                        get(PATH_WITH_ID, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.address").value(ADDRESS));
    }

    @Test
    void getAddressById_shouldBeNotFoundAddress() throws Exception {
        //given
        var addressDto = new AddressDto();
        addressDto.setAddress(ADDRESS);

        var id = findOrInsertTestAddress(addressDto).getId();
        var notExistId = id + 1;

        //when
        mockMvc.perform(
                        get(PATH_WITH_ID, notExistId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Failed"));
    }

    @Test
    void deleteAddressById_shouldBeDeleteAddressSuccessful() throws Exception {
        //given
        var addressDto = new AddressDto();
        addressDto.setAddress(ADDRESS);
        var id = findOrInsertTestAddress(addressDto).getId();

        //when
        var result = 1;
        mockMvc.perform(
                        delete(PATH_WITH_ID, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(result));
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        var expectedCountLines = 0;
        Assertions.assertThat(actualCountLines).isEqualTo(expectedCountLines);
    }

    @Test
    void deleteAddressById_shouldBeNotFoundAddress() throws Exception {
        //given
        var addressDto = new AddressDto();
        addressDto.setAddress(ADDRESS);
        var id = findOrInsertTestAddress(addressDto).getId();
        var notExistId = id + 1;

        //when
        mockMvc.perform(
                        delete(PATH_WITH_ID, notExistId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Failed"));
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        var expectedCountLines = 1;
        Assertions.assertThat(actualCountLines).isEqualTo(expectedCountLines);
    }

    private AddressEntity findOrInsertTestAddress(AddressDto addressDto) {
        return repository.findOrInsertAddress(addressMapper.toEntity(addressDto));
    }
}