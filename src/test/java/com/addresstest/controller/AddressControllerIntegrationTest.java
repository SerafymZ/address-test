package com.addresstest.controller;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;
import com.addresstest.initializer.MsSQLServer;
import com.addresstest.mapper.AddressMapper;
import com.addresstest.reposirory.AddressRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @AfterEach
    void clearDb() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "address");
    }

    @Test
    void findOrInsertAddress_shouldBeInsertAddressSuccessfully() throws Exception {
        //given
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isZero();

        var addressDto = new AddressDto(null, ADDRESS);

        //when
        mockMvc.perform(
                        post(PATH)
                                .content(objectMapper.writeValueAsString(addressDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.address").value(ADDRESS));

        actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isEqualTo(1);
    }

    @Test
    void findOrInsertAddress_shouldBeFindAddressSuccessfully() throws Exception {
        //given
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isZero();

        var addressDto = new AddressDto(null, ADDRESS);
        findOrInsertTestAddress(addressDto);
        actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isEqualTo(1);

        //when
        mockMvc.perform(
                        post(PATH)
                                .content(objectMapper.writeValueAsString(addressDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.address").value(ADDRESS));

        actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        var expectedCountLines = 1;
        assertThat(actualCountLines).isEqualTo(expectedCountLines);
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
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isZero();
        var addressDto = new AddressDto(null, ADDRESS);

        var id = findOrInsertTestAddress(addressDto).getId();

        //when
        mockMvc.perform(
                        get(PATH_WITH_ID, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.address").value(ADDRESS));

        var expectedCountLines = 1;
        actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isEqualTo(expectedCountLines);
    }

    @Test
    void getAddressById_shouldBeNotFoundAddress() throws Exception {
        //given
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isZero();

        var addressDto = new AddressDto(null, ADDRESS);

        var id = findOrInsertTestAddress(addressDto).getId();
        var notExistId = id + 1;

        //when
        mockMvc.perform(
                        get(PATH_WITH_ID, notExistId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Failed"));

        var expectedCountLines = 1;
        actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isEqualTo(expectedCountLines);
    }

    @Test
    void deleteAddressById_shouldBeDeleteAddressSuccessful() throws Exception {
        //given
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isZero();

        var addressDto = new AddressDto(null, ADDRESS);
        var id = findOrInsertTestAddress(addressDto).getId();

        //when
        var result = 1;
        mockMvc.perform(
                        delete(PATH_WITH_ID, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(result));
        actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        var expectedCountLines = 0;
        assertThat(actualCountLines).isEqualTo(expectedCountLines);
    }

    @Test
    void deleteAddressById_shouldBeNotFoundAddress() throws Exception {
        //given
        var actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        assertThat(actualCountLines).isZero();

        var addressDto = new AddressDto(null, ADDRESS);
        var id = findOrInsertTestAddress(addressDto).getId();
        var notExistId = id + 1;

        //when
        mockMvc.perform(
                        delete(PATH_WITH_ID, notExistId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Failed"));
        actualCountLines = JdbcTestUtils.countRowsInTable(jdbcTemplate, "address");
        var expectedCountLines = 1;
        assertThat(actualCountLines).isEqualTo(expectedCountLines);
    }

    private AddressEntity findOrInsertTestAddress(AddressDto addressDto) {
        return repository.findOrInsertAddress(addressMapper.toEntity(addressDto));
    }
}