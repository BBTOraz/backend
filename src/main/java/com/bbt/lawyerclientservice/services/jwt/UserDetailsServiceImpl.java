package com.bbt.lawyerclientservice.services.jwt;

import com.bbt.lawyerclientservice.entity.Client;
import com.bbt.lawyerclientservice.entity.Lawyer;
import com.bbt.lawyerclientservice.repository.UserRepositoryForLogIn;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepositoryForLogIn repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.bbt.lawyerclientservice.entity.User> user = repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        com.bbt.lawyerclientservice.entity.User currentUser = user.get();
        String username = currentUser.getEmail();
        String password = null;
        Collection<? extends GrantedAuthority> authorities;

        if (currentUser instanceof Client) {
            Client client = (Client) currentUser;
            password = client.getPassword();
            String role = client.getRole().name();
            authorities = Collections.singleton(new SimpleGrantedAuthority(role));
        } else if (currentUser instanceof Lawyer) {
            Lawyer lawyer = (Lawyer) currentUser;
            password = lawyer.getPassword();
            String role = lawyer.getRole().name();
            authorities = Collections.singleton(new SimpleGrantedAuthority(role));
        } else {
            throw new IllegalStateException("Unsupported user type");
        }
        if (password == null) {
            throw new UsernameNotFoundException("User password is null for email: " + email);
        }
        return new User(username, password, authorities);
    }
}
