package com.example.bitirme.login.dto;

public class LoginResponseDto {
    private String mesaj;
    private boolean basarili;

    // Constructor
    public LoginResponseDto(String mesaj, boolean basarili) {
        this.mesaj = mesaj;
        this.basarili = basarili;
    }

    // Getter ve Setter
    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public boolean isBasarili() {
        return basarili;
    }

    public void setBasarili(boolean basarili) {
        this.basarili = basarili;
    }
}
