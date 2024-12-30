package com.example.bitirme.kullanicicrud.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class GirisCikisIzinBilgileriDTO {
    private boolean izinHakkiVarMi;
    private LocalDate izinBaslangicTarihi;
    private LocalDate izinBitisTarihi;
    private Long kullaniciId;  // Kullanıcı ID'sini buraya ekliyoruz

    // Constructor
    public GirisCikisIzinBilgileriDTO() {
        this.izinHakkiVarMi = izinHakkiVarMi;
        this.izinBaslangicTarihi = izinBaslangicTarihi;
        this.izinBitisTarihi = izinBitisTarihi;
        this.kullaniciId = kullaniciId;
    }

    public GirisCikisIzinBilgileriDTO(boolean izinHakkiVarMi, LocalDate izinBaslangicTarihi, LocalDate izinBitisTarihi) {
        this.izinHakkiVarMi = izinHakkiVarMi;
        this.izinBaslangicTarihi = izinBaslangicTarihi;
        this.izinBitisTarihi = izinBitisTarihi;
        this.kullaniciId = 0L;
    }

    public boolean isIzinHakkiVarMi() {
        return izinHakkiVarMi;
    }

    public void setIzinHakkiVarMi(boolean izinHakkiVarMi) {
        this.izinHakkiVarMi = izinHakkiVarMi;
    }
}
