package com.codechallege.rivo.models.response;

import java.util.List;

public class ResponseHelper {


    public static <T> BaseResponse createBaseResponse(List<T> data) {
        return BaseResponse.builder()
                .responseData(data)
                .build();
    }

    public static <T> BaseResponse createBaseResponse(T data) {
        return BaseResponse.builder()
                .responseData(data)
                .build();
    }

    public static BaseResponse createBaseResponse(String massage) {
        return BaseResponse.builder()
                .message(massage)
                .build();
    }

    public static BaseResponse createBaseResponse() {
        return BaseResponse.builder()
                .build();
    }
}

