package com.addresstest.reposirory;

import com.addresstest.entity.AddressEntity;
import com.addresstest.exception.NotFoundAddressException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public AddressEntity saveAddress(AddressEntity addressEntity) {
        var sql = "INSERT INTO address (address) OUTPUT inserted.* VALUES (:address)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("address", addressEntity.getAddress());
        return namedJdbcTemplate
                .queryForObject(sql, parameters, new BeanPropertyRowMapper<>(AddressEntity.class));
    }

    @Override
    public AddressEntity getAddressById(long addressId) {
        var sql = "SELECT id, address FROM address WHERE id=:addressId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("addressId", addressId);
        try {
            return namedJdbcTemplate
                    .queryForObject(sql, parameters, new BeanPropertyRowMapper<>(AddressEntity.class));
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundAddressException("There is no address with ID = " + addressId + " in database.");
        }
    }

    @Override
    public AddressEntity updateAddress(AddressEntity addressDto) {
        var sql = "UPDATE address SET address=:address OUTPUT inserted.* WHERE id=:addressId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("addressId", addressDto.getId());
        parameters.addValue("address", addressDto.getAddress());
        return namedJdbcTemplate
                .queryForObject(sql, parameters, new BeanPropertyRowMapper<>(AddressEntity.class));
    }

    @Override
    public int deleteAddressById(long addressId) {
        var sql = "DELETE FROM address WHERE id=:addressId";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("addressId", addressId);
        return namedJdbcTemplate.update(sql, parameters);
    }
}
