package com.bbt.lawyerclientservice.entity;

import com.bbt.lawyerclientservice.model.ClientDTO;
import com.bbt.lawyerclientservice.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "client")
@Data
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    private String password;

    public ClientDTO getClientDTO() {
        return ClientDTO.builder()
                .firstname(firstname)
                .lastname(lastname)
                .role(role)
                .email(getEmail())
                .phone(getPhone())
                .password(password)
                .build();

    }
}
