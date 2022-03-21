package com.addresstest.entity;

import lombok.Data;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Data
public class AddressEntity {
    private long id;
    private String address;

    public MapSqlParameterSource getSqlParams() {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("address_id", id);
        sqlParameterSource.addValue("address", address);
        return sqlParameterSource;
    }
}
