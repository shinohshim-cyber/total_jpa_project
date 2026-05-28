package com.my.total_jpa_back.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class HelloResponse {
    //  응답 결과를 담아서 보내는 DTO
    private String message;
    private int age;
}
