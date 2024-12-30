package com.example.bitirme.login.dto;

public class LoginRequestDto {
    private String email;
    private String kullaniciSifre;

    // Getter ve Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKullaniciSifre() {
        return kullaniciSifre;
    }

    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }
}
