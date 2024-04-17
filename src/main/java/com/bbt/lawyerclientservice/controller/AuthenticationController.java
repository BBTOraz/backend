package com.bbt.lawyerclientservice.controller;

import com.bbt.lawyerclientservice.model.AuthenticationRequest;
import com.bbt.lawyerclientservice.model.ClientDTO;
import com.bbt.lawyerclientservice.model.SignUpRequestDTO;
import com.bbt.lawyerclientservice.services.authService.ClientAuthService;
import com.bbt.lawyerclientservice.services.jwt.UserDetailsServiceImpl;
import com.bbt.lawyerclientservice.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Iterator;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class AuthenticationController {
    private final ClientAuthService authService;
    private final AuthenticationManager manager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/register")
    public ResponseEntity<?> tryRegistrateClient(@RequestBody SignUpRequestDTO signUpRequestDTO){
        if(authService.isClientExist(signUpRequestDTO.getEmail())){
            return new ResponseEntity<>("Client already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        ClientDTO clientDTO = authService.signUpClient(signUpRequestDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public void createAuthToken(@RequestBody AuthenticationRequest authRequest, HttpServletResponse response) throws IOException, JSONException {
        try{
            manager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        Iterator<? extends GrantedAuthority> iterator = userDetails.getAuthorities().iterator();
        GrantedAuthority role = null;
        if(iterator.hasNext()){
            role = iterator.next();
        }
        response.getWriter().write(new JSONObject()
                .put("userId", userDetails.getUsername())
                .put("role", role.getAuthority())
                .toString()
        );

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization," + " X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX+jwt);
    }

}
