package com.example.bitirme.kullanicicrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VeliBigileriKayitDTO {

    private String veliAdi;
    private String veliSoyadi;
    private String veliTelefon;
    private String veliNotu;

    public VeliBigileriKayitDTO() {}
}
