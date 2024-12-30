package com.example.bitirme.login.controller;

import com.example.bitirme.login.dto.LoginRequestDto;
import com.example.bitirme.login.dto.LoginResponseDto;
import com.example.bitirme.login.entity.Admin;
import com.example.bitirme.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/admin-giris")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        return loginService.adminGirisYap(loginRequest);
    }

    @PostMapping("/ekle")
    public Admin adminEkle(@RequestBody Admin admin) {
        return loginService.adminEkle(admin);
    }
}

