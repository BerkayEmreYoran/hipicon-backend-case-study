package com.hipicon.casestudy.customer;

import com.hipicon.casestudy.auth.dto.LoginRequestDTO;
import com.hipicon.casestudy.auth.dto.LoginResponseDTO;
import com.hipicon.casestudy.exception.AppException;
import com.hipicon.casestudy.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(CustomerRepository customerRepository, JwtTokenProvider jwtTokenProvider) {
        this.customerRepository = customerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        return customerRepository.findByEmail(dto.getEmail())
                .filter(customer -> customer.getPassword().equals(dto.getPassword()))
                .map(customer -> {
                    String token = jwtTokenProvider.generateToken(customer.getEmail());
                    return new LoginResponseDTO(token);
                })
                .orElseThrow(() ->
                        new AppException(
                                "Kullanıcı bulunamadı veya kullanıcı adı/şifre yanlış"
                        )
                );
    }

}
