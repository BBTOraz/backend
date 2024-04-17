package com.bbt.lawyerclientservice.services.authService;

import com.bbt.lawyerclientservice.model.ClientDTO;
import com.bbt.lawyerclientservice.model.SignUpRequestDTO;

public interface ClientAuthService {
    ClientDTO signUpClient(SignUpRequestDTO signUp);
    Boolean isClientExist(String email);

}
