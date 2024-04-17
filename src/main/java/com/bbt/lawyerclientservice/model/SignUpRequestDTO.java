package com.bbt.lawyerclientservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequestDTO {
    private String firstname;
    private String lastname;
    private String phone;
    private String password;
    private String email;
}
