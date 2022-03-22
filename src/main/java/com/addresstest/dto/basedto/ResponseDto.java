package com.addresstest.dto.basedto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ResponseDto<T> extends BaseResponseDto{
    private T data;

    public ResponseDto(T data) {
        this.data = data;
    }

    public static <T> ResponseDto<T> okResponseDto(T data) {

        var responseDto = new ResponseDto<T>(data);
        responseDto.setStatus(Status.OK);

        return responseDto;
    }

    public static <T> ResponseDto<T> failedResponseDto(T data) {

        var responseDto = new ResponseDto<T>(data);
        responseDto.setStatus(Status.Failed);

        return responseDto;
    }
}
