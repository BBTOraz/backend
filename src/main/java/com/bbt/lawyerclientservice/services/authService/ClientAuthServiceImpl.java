package com.bbt.lawyerclientservice.services.authService;

import com.bbt.lawyerclientservice.model.ClientDTO;
import com.bbt.lawyerclientservice.model.SignUpRequestDTO;
import com.bbt.lawyerclientservice.entity.*;
import com.bbt.lawyerclientservice.entity.enums.UserRole;
import com.bbt.lawyerclientservice.repository.ClientRepository;
import com.bbt.lawyerclientservice.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAuthServiceImpl implements ClientAuthService {
    private final ClientRepository clientRepository;
    private final ContactRepository contactRepository;
    @Override
    public ClientDTO signUpClient(SignUpRequestDTO signUp){
        Client newClient = new Client();

        Contact contact = new Contact();
        contact.setEmail(signUp.getEmail());
        contact.setPhone(signUp.getPhone());

        newClient.setFirstname(signUp.getFirstname());
        newClient.setLastname(signUp.getLastname());
        newClient.setContact(contact);
        newClient.setPassword(new BCryptPasswordEncoder().encode(signUp.getPassword()));

        newClient.setRole(UserRole.CLIENT);

        clientRepository.save(newClient);

        return newClient.getClientDTO();
    }

    @Override
    public Boolean isClientExist(String email) {
        return contactRepository.existsByEmail(email);
    }

}
