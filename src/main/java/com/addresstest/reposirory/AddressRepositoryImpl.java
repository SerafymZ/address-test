package com.addresstest.reposirory;

import com.addresstest.entity.AddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl implements AddressRepository{

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public AddressEntity saveAddress(AddressEntity addressEntity) {
        var sql = "INSERT INTO address (address) OUTPUT inserted.* VALUES (:address)";
        return namedJdbcTemplate
                .queryForObject(sql, addressEntity.getSqlParams(), new BeanPropertyRowMapper<>(AddressEntity.class));
    }

    @Override
    public AddressEntity getAddressById(long addressId) {
        var sql = "SELECT id, address FROM address WHERE id=:addressId";
        return namedJdbcTemplate
                .queryForObject(sql, getParameters(addressId), new BeanPropertyRowMapper<>(AddressEntity.class));
    }

    @Override
    public AddressEntity updateAddress(AddressEntity addressDto) {
        var sql = "UPDATE address SET address=:address OUTPUT inserted.* WHERE id=:addressId";
        var parameters = getParameters(addressDto.getId());
        parameters.addValue("address", addressDto.getAddress());
        return namedJdbcTemplate
                .queryForObject(sql, parameters, new BeanPropertyRowMapper<>(AddressEntity.class));
    }

    @Override
    public int deleteAddressById(long addressId) {
        var sql = "DELETE FROM address WHERE id=:addressId";
        return namedJdbcTemplate.update(sql, getParameters(addressId));
    }

    private MapSqlParameterSource getParameters(long addressId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("addressId", addressId);
        return parameters;
    }
}
