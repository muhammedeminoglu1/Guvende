package com.example.bitirme.izinislemleri.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class KullaniciIzinBilgileriDTO {

    private String adi;
    private String soyadi;
    private LocalDate izinBaslangicTarihi;
    private LocalDate izinBitisTarihi;

    public KullaniciIzinBilgileriDTO(String adi, String soyadi, LocalDate izinBaslangicTarihi, LocalDate izinBitisTarihi) {
        this.adi = adi;
        this.soyadi = soyadi;
        this.izinBaslangicTarihi = izinBaslangicTarihi;
        this.izinBitisTarihi = izinBitisTarihi;
    }

}
