package com.hipicon.casestudy.auth;

import com.hipicon.casestudy.auth.dto.LoginRequestDTO;
import com.hipicon.casestudy.auth.dto.LoginResponseDTO;
import com.hipicon.casestudy.base.BaseControllerV1;
import com.hipicon.casestudy.base.Response;
import com.hipicon.casestudy.customer.CustomerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseControllerV1 {

    private final CustomerService customerService;


    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public Response<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO responseDTO = customerService.login(dto);
        return  ok(responseDTO);
    }

}

