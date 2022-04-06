package com.addresstest.dto.basedto;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseDto<T> extends BaseResponseDto{
    private T data;

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

    public static <T> ResponseDto<T> failedResponseDto(List<ErrorDto> errors) {

        var responseDto = new ResponseDto();
        responseDto.setStatus(Status.Failed);
        responseDto.setErrors(errors);

        return responseDto;
    }
}
