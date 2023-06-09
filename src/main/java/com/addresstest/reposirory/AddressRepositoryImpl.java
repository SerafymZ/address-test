package com.addresstest.reposirory;

import com.addresstest.entity.AddressEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public AddressEntity findOrInsertAddress(AddressEntity addressEntity) {
        var sql = "IF NOT EXISTS(SELECT id, address FROM [address] WHERE address LIKE :address)\n" +
                "BEGIN\n" +
                "  INSERT INTO [address] (address) OUTPUT inserted.* VALUES\n" +
                "  (:address)\n" +
                "END\n" +
                "ELSE\n" +
                "BEGIN\n" +
                "  SELECT id, address FROM [address] WHERE address LIKE :address\n" +
                "END";
        var parameters = new MapSqlParameterSource();
        parameters.addValue("address", addressEntity.getAddress());
        return namedJdbcTemplate
                .queryForObject(sql, parameters, new BeanPropertyRowMapper<>(AddressEntity.class));
    }

    @Override
    public Optional<AddressEntity> getAddressById(long addressId) {
        var sql = "SELECT id, address FROM address WHERE id=:addressId";
        var parameters = new MapSqlParameterSource();
        parameters.addValue("addressId", addressId);
        AddressEntity entity;
        try {
            entity = namedJdbcTemplate
                    .queryForObject(sql, parameters, new BeanPropertyRowMapper<>(AddressEntity.class));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public int deleteAddressById(long addressId) {
        var sql = "DELETE FROM address WHERE id=:addressId";
        var parameters = new MapSqlParameterSource();
        parameters.addValue("addressId", addressId);
        return namedJdbcTemplate.update(sql, parameters);
    }
}
