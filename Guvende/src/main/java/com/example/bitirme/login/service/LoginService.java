package com.example.bitirme.login.service;

import com.example.bitirme.login.dto.LoginRequestDto;
import com.example.bitirme.login.dto.LoginResponseDto;
import com.example.bitirme.kullanicicrud.entity.Kullanicilar;
import com.example.bitirme.kullanicicrud.repository.KullaniciRepository;
import com.example.bitirme.login.entity.Admin;
import com.example.bitirme.login.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final AdminRepository adminRepository;

    public LoginService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public LoginResponseDto adminGirisYap(LoginRequestDto loginRequest) {
        Optional<Admin> admin = adminRepository.findByEmailAndSifre(loginRequest.getEmail(), loginRequest.getKullaniciSifre());

        if (admin.isPresent()) {
            return new LoginResponseDto("Admin girişi başarılı!", true);
        }
        return new LoginResponseDto("Geçersiz email veya şifre!", false);
    }
    public Admin adminEkle(Admin admin) {
        // Eğer email zaten varsa hata dönebiliriz.
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Bu email zaten kayıtlı!");
        }
        return adminRepository.save(admin);
    }
}
