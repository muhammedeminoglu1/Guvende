package com.example.bitirme.kullanicicrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class KullaniciRequestDTO {
    private Long id;
    private String adi;
    private String soyadi;
    private String adres;
    private boolean kullaniciMuvcutMu;
    private String email;
    private String kisiselTelefon;
    private LocalDate kayitTarihi;
    private LocalDate cikisTarihi;
    private String kullaniciSifre;

    private List<GirisCikisIzinBilgileriDTO> girisCikisIzinBilgileriDTO = new ArrayList<>();  // Boş bir liste

    private VeliBigileriKayitDTO veliBilgileriDTO = new VeliBigileriKayitDTO(); // Boş bir nesne

    public KullaniciRequestDTO() {

    }

}
