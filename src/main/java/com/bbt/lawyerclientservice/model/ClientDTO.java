package com.bbt.lawyerclientservice.model;

import com.bbt.lawyerclientservice.entity.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {
    private String firstname;
    private String lastname;
    private UserRole role;
    private String phone;
    private String password;
    private String email;
}
