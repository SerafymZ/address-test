package com.addresstest.service;

import com.addresstest.dto.AddressDto;
import com.addresstest.entity.AddressEntity;
import com.addresstest.exception.NotFoundAddressException;
import com.addresstest.mapper.AddressMapper;
import com.addresstest.reposirory.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AddressServiceImpl.class)
class AddressServiceImplTest {

    private static final long ID = 1L;
    private static final String ADDRESS = "Canada";

    @Autowired
    AddressServiceImpl addressService;

    @MockBean
    AddressMapper addressMapper;

    @MockBean
    AddressRepository addressRepository;

    @Test
    void findOrInsertAddress_shouldBeThrownNullPointerException() {
        //when
        assertThatThrownBy(() ->
                addressService.findOrInsertAddress(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void findOrInsertAddress_shouldBeFindAddressSuccessfully() {
        //given
        var addressDto = new AddressDto(null, ADDRESS);
        var addressEntity = new AddressEntity(null, ADDRESS);
        var addressEntityResult = new AddressEntity(ID, ADDRESS);
        var expectedResult = new AddressDto(ID, ADDRESS);

        when(addressMapper.toEntity(addressDto)).thenReturn(addressEntity);
        when(addressRepository.findOrInsertAddress(addressEntity)).thenReturn(addressEntityResult);
        when(addressMapper.toDto(addressEntityResult)).thenReturn(expectedResult);

        //when
        var actualResult = addressService.findOrInsertAddress(addressDto);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);

        verify(addressMapper, times(1)).toEntity(addressDto);
        verify(addressRepository, times(1)).findOrInsertAddress(addressEntity);
        verify(addressMapper, times(1)).toDto(addressEntityResult);
    }

    @Test
    void getAddressById_shouldBeThrewNotFoundAddressException() {
        //given
        when(addressRepository.getAddressById(ID)).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> addressService.getAddressById(ID)).isInstanceOf(NotFoundAddressException.class);

        //then
        verify(addressRepository, times(1)).getAddressById(ID);
    }

    @Test
    void getAddressById_shouldBeGetAddressDtoSuccessful() {
        //given
        var addressEntity = new AddressEntity(ID, ADDRESS);
        when(addressRepository.getAddressById(ID)).thenReturn(Optional.of(addressEntity));

        var expectedResult = new AddressDto(ID, ADDRESS);
        when(addressMapper.toDto(addressEntity)).thenReturn(expectedResult);

        //when
        var actualResult = addressService.getAddressById(ID);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);
        verify(addressRepository, times(1)).getAddressById(ID);
        verify(addressMapper, times(1)).toDto(addressEntity);
    }

    @Test
    void deleteAddressById_shouldBeThrewNotFoundAddressException() {
        //given
        when(addressRepository.getAddressById(ID)).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> addressService.deleteAddressById(ID)).isInstanceOf(NotFoundAddressException.class);

        //that
        verify(addressRepository, times(1)).getAddressById(ID);
    }

    @Test
    void deleteAddressById_shouldBeReturnedIntSuccessful() {
        //given
        var addressEntity = new AddressEntity(ID, ADDRESS);
        when(addressRepository.getAddressById(ID)).thenReturn(Optional.of(addressEntity));
        var expectedResult = 1;
        when(addressRepository.deleteAddressById(ID)).thenReturn(expectedResult);

        //when
        var actualResult = addressService.deleteAddressById(ID);

        //then
        assertThat(actualResult).isEqualTo(expectedResult);

        verify(addressRepository, times(1)).getAddressById(ID);
        verify(addressRepository, times(1)).deleteAddressById(ID);
    }
}