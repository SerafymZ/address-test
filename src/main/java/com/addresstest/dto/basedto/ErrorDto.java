package com.addresstest.dto.basedto;

import lombok.Data;

@Data
public class ErrorDto {
    private Integer code;
    private Integer source;
    private String message;
}
